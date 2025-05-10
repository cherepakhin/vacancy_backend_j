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

Порт приложения 8090 установлен в application.yaml:

````yaml
server:
  port: 8098

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

````shell
http :8090/api/echo/MESSAGE_ECHO

MESSAGE_ECHO
````

````shell
http http://192.168.1.20:8090/api/vacancy/2
http http://127.0.0.1:8090/api/vacancy/2

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

#### OpenApi

Доступно по адресу http://127.0.0.1:8090/api/vacancy-api-docs

#### Swagger

Реализован интерфейс Swagger по адресу [http://localhost:8090/api/swagger-ui/index.html](http://localhost:8090/api/swagger-ui/index.html) 
