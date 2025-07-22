### Backend проекта "Вакансии" на Java и Camunda

#### Цель

Создать приложение на Java и Camunda для проекта "Ищу работу"

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

Rest:

````shell
http :8090/api/echo/MESSAGE_ECHO
>MESSAGE_ECHO
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

Тестовые запросы:

Для HTTPS:

````shell
$ https https://v.perm.ru:8443/api/vacancy/
````

https - утилита из [https://httpie.io](https://httpie.io)

````shell
https://127.0.0.1:8443/api/echo/MESSAGE_ECHO

MESSAGE_ECHO
````

````shell
https https://127.0.0.1:8443/api/company/2
{
    "n": 2,
    "name": "Company 2"
}

https://127.0.0.1:8443/api/vacancy/2

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

https://127.0.0.1:8443/api/vacancy/
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

Для создания _war_ файлов в build.gradle добавить:

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

Установить переменные для доступа к базе данных:

````shell
export PG_USER=vasi
export PG_PASS=pass
````

На сервере (не на ноутбуке, папка __v:~/temp/vacancy__):

````shell
v:~/temp/vacancy$ java -jar vacancy_backend-0.0.1-SNAPSHOT.jar
````

Для тестирования prod на https://v.perm.ru можно использовать [https://httpie.io/app](https://httpie.io/app). Пример запроса GET на [https://v.perm.ru:8443/api/vacancy/](https://v.perm.ru:8443/api/vacancy/) :

![httpie](doc/httpie.png)


#### Тесты на prod

````shell
https https://v.perm.ru:8443/api/echo/MESSAGE_ECHO
https https://v.perm.ru:8443/api/company/2     
````

HTTP (не httpS) запросы на prod не работают.

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
