### Backend проекта "Вакансии" на Java и Camunda

#### Цель

Создать приложение на Java и Camunda для проекта "Ищу работу"


ОБЯЗАТЕЛЬНО установить имя и пароль для доступа к базе данных postgres:

````shell
export PG_USER=user
export PG_PASSWORD=pass

````

Прогон unit тестов:

````shell
./gradlew test
````

Запуск приложения:

````shell
./gradlew bootRun
````

Порт приложения 8443 установлен в application.yaml:

````yaml
server:
  port: 8443

````

Использован flyway. Для обновления структуры базы данных выполнить:

````shell
./gradlew flywayMigrate
````

или

````shell

./gradlew flywayMigrate -Dflyway.url=jdbc:postgresql://192.168.1.20:5432/vacancy -Dflyway.user=vasi -Dflyway.password=pass
````
 
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

http POST :8090/api/vacancy/find < src/resources/VacancyCriterySearchByName.json

Для работы с базой данных использован org.springframework.data.jpa.domain.Specification. Пример в ru.perm.v.vacancy_j.service.impl.VacancyServiceImpl.findByCritery(...) :

````java
    public List<VacancyDto> findByCritery(VacancyCriterySearch criterySearch) {
        log.info(format("Find vacancy by criterySearch: %s", criterySearch));

        Specification<VacancyEntity> spec = VacancySpecifications.hasNGreaterThan(-1L);

        if (criterySearch.getNn().size() > 0) {
            log.info("add NN to critery");
            spec = spec.and(VacancySpecifications.N_In(criterySearch.getNn()));
        }

        if (!criterySearch.getByName().isEmpty()) {
            log.info("add NAME to critery");
            spec = spec.and(VacancySpecifications.hasTitleLike(criterySearch.getByName()));
        }

        List<VacancyEntity> entities 1= vacancyRepository.findAll(spec);
        for (VacancyEntity v : entities) {
            log.info(format("Find vacancy by title %s", v.toString()));
        }
        return vacancyMapper.toListDto(entities);
````

Тест ru.perm.v.vacancy_j.service.impl.VacancyServiceImplIntegrationTest:

````shell
    @Test
    void findByCriteryWithInNNAndLikeName() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        VacancyCriterySearch criterySearch = new VacancyCriterySearch();
        criterySearch.setNn(List.of(1L, 3L));
        criterySearch.setByName("%Company 1");
        List<VacancyDto> vacancies = vacancyService.findByCritery(criterySearch);

        assertEquals(1, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN());
     }
````

#### Swagger

Доступен по [https://127.0.0.1:8443/api/swagger-ui/index.html](https://127.0.0.1:8443/api/swagger-ui/index.html)

![swagger](doc/swagger.png)

(Swagger работает через __HTTPS__)

 Доступ на сервере [https://v.perm.ru:8443/api/swagger-ui/index.html](https://v.perm.ru:8443/api/swagger-ui/index.html).

#### Тестовые запросы:

__Для HTTPS.__

__1.__ Можно использовать swagger (см. [Swagger](#swagger)).


__2.__ Можно использовать утилиту HTTPIE  [https://httpie.io](https://httpie.io):

Для проверок на локальном компьютере. __КЛЮЧЕВОЙ ПАРАМЕТР__ --verify=no:

````shell
$ https --verify=no https://v:8443/api/echo/MESSAGE
HTTP/1.1 200 
Connection: keep-alive
Content-Length: 7
Content-Type: text/plain;charset=ISO-8859-1
Keep-Alive: timeout=60
Vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers

MESSAGE
````

````shell
$ https --verify=no https://127.0.0.1:8443/api/company/ | jq
(https --verify=no https://127.0.0.1:8443/api/company/ | jq)
[
    {
      "n": 2,
      "name": "Company 2"
    }
]
````

При работе приложением на сервере этот параметр не нужен, т.к. на сервере https настроен.


````shell
$ https https://v.perm.ru:8443/api/vacancy/2
(https --verify=no https://127.0.0.1:8443/api/vacancy/2 | jq)
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers

{
    "comment": "Comment 2",
    "company": {
        "n": 1,
        "name": "Company 1"
    },
    "description": "Description Vacancy 2 Company 1",
    "n": 2,
    "source": "Link 21",
    "status": "in_work",
    "title": "Vacancy 2 Company 1"
}

$ http --verify=no https://v.perm.ru:8443/api/company/2 | jq
{
  "n": -1,
  "name": "-"
}
````

(работает на сервере, НЕ РАБОТАЕТ на ноуте)

__3.__ Ручные тестовые запросы HTTPS через CURL (использовать ключ --insecure):

````shell
curl -X 'GET' --insecure https://127.0.0.1:8443/api/company/2
curl -X 'GET' --insecure https://192.168.1.57:8443/api/company/2 
````

форматированный вывод и статистика (использован jq):

````shell
curl -X 'GET' --insecure https://127.0.0.1:8443/api/company/2 | jq
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    19    0    19    0     0    463      0 --:--:-- --:--:-- --:--:--   463
{
  "n": -1,
  "name": "-"
}
````

__4.__ Ручные тестовые запросы через браузер:
[https://127.0.0.1:8443/api/company/2](https://127.0.0.1:8443/api/company/2)

````
{
    "n": 2,
    "name": "Company 2"
}
````

[https://127.0.0.1:8443/api/vacancy/2](https://127.0.0.1:8443/api/vacancy/2)

````
{
    "comment": "",
    "company": {
        "n": 1,
        "name": "Company 1"
    },
    "description": "Description Vacancy 2 Company 1",
    "n": 2,
    "source": "",
    "title": "Vacancy 2 Company 1"
}
````

[https://127.0.0.1:8443/api/vacancy/](https://127.0.0.1:8443/api/vacancy/)

````
[
    {
        "comment": "",
        "company": {
            "n": 1,
            "name": "Company 1"
        },
        "description": "Description Vacancy 2 Company 1",
        "n": 2,
        "source": "",
        "title": "Vacancy 2 Company 1"
    },
    ....
]
````

#### Сборка jar файла

Подключен gradle plugin в build.gradle:

````shell
springBoot {
    buildInfo()
}
````

Сборка:

````shell
./gradlew bootJar
````

[./build_jar.sh](./build_jar.sh)

Запуск jar файла:

````shell
/usr/lib/jvm/java-17-openjdk-amd64/bin/java -jar vacancy_backend-0.0.1-SNAPSHOT.jar
````

запуск на другом порту:

````shell
/usr/lib/jvm/java-17-openjdk-amd64/bin/java -Dserver.port=8090 -jar vacancy_backend-0.0.1-SNAPSHOT.jar
````

#### WAR

Для создания _war_ файлв в build.gradle добавить:

````shell
apply plugin:'war'

war {
    enabled=true
}

````

сборка:

````shell
./gradlew bootWar
````

#### Swagger

Swagger доступен по адресу [https://127.0.0.1:8443/api/swagger-ui/index.html](https://127.0.0.1:8443/api/swagger-ui/index.html) 

#### PROD запуск

Выполнить [./build_jar.sh](./build_jar.sh). Файл build/libs/vacancy_backend-0.0.1-SNAPSHOT.jar скопировать на web сервер. Запустить backend:

````shell
/usr/lib/jvm/java-17-openjdk-amd64/bin/java -jar vacancy_backend-0.0.1-SNAPSHOT.jar
````

На этом же сервере разместить [~/prog/js/vacancy_frontend_17](https://github.com/cherepakhin/vacancy_frontend_17). Как заместить в проекте описано. (скопировать каталог vacancy_frontend_17/build в каталог apache2 /var/www/main/vacancies).

#### Настройка HTTPS

Делается в секции __server__ [application.yaml](./src/main/resources/application.yaml):
````yaml
server:
  port: 8443
  ssl.key-store: /home/vasi/prog/sert/keystore.p12
  ssl.key-store-password: B..67
  ssl.keyStoreType: PKCS12
  ssl.keyAlias: tomcat

security.require-ssl: true
````

Сертификат 	Let's Encrypt.

На сервере (не на ноутбуке!!!) в папке /home/vasi/prog/sert.

#### Запуск

На сервере (не на ноутбуке, папка __v:~/temp/vacancy__):

````shell
v:~/temp/vacancy$ java -jar vacancy_backend-0.0.1-SNAPSHOT.jar
````

Для тестирования prod на https://v.perm.ru можно использовать [https://httpie.io/app](https://httpie.io/app). Пример запроса GET на [https://v.perm.ru:8443/api/vacancy/](https://v.perm.ru:8443/api/vacancy/) :

![httpie](doc/httpie.png)

#### Ручные тесты на localhost через __браузер___

[https://127.0.0.1:8443/api/echo/MESSAGE](https://127.0.0.1:8443/api/echo/MESSAGE)
[https://127.0.0.1:8443/api/company/](https://127.0.0.1:8443/api/company/)
    
#### Тесты на prod

````shell
https https://v.perm.ru:8443/api/echo/MESSAGE_ECHO
https https://v.perm.ru:8443/api/company/2     
````

Можно использовать swagger [https://127.0.0.1:8443/api/swagger-ui/index.html#/](https://127.0.0.1:8443/api/swagger-ui/index.html#/)

#### Frontend

Сделан frontend [https://v.perm.ru/vacancies/](https://v.perm.ru/vacancies/). Проект с frontend в [https://github.com/cherepakhin/vacancy_frontend_17](https://github.com/cherepakhin/vacancy_frontend_17). Адрес backend во frontend  проекте указан в [http-common.js](https://github.com/cherepakhin/vacancy_frontend_17/blob/main/src/http-common.js):

````shell
export default axios.create({
  baseURL: "https://v.perm.ru:8443/api",

  mode: "no-cors",
  headers: {
    "Content-type": "application/json"
  }
});
````

Версии:

ветка v1 - сделано CRUD без авторизации
ветка auth - авторизация REST

### Размещение на linux сервере

В файле [./doc/vacancy_backend.service](././doc/vacancy_backend.service) пример настройки сервиса для Linux.

````shell
[Unit]
Description=Vacancy service
After=network.target

[Service]
Type=simple
ExecStart=/usr/lib/jvm/java-17-openjdk-amd64/bin/java -Dserver.port=8443 -jar /home/vasi/temp/vacancy/vacancy_backend-0.0.1-SNAPSHOT.jar
TimeoutStartSec=0

[Install]
WantedBy=default.target
````

Манипуляции для настройки

````shell
$ sudo systemctl daemon-reload
$ sudo systemctl enable vacancy_backend.service
$ sudo systemctl start vacancy_backend.service
$ sudo systemctl status vacancy_backend.service

● vacancy_backend.service - Vacancy service
     Loaded: loaded (/etc/systemd/system/vacancy_backend.service; enabled; pres>
     Active: active (running) 
   Main PID: 2418855 (java)
      Tasks: 42 (limit: 13882)
     Memory: 279.2M (peak: 289.5M)
        CPU: 26.100s
     CGroup: /system.slice/vacancy_backend.service
             └─2418855 /usr/lib/jvm/java-17-openjdk-amd64/bin/java -Dserver.por>

````

