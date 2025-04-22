### Backend проекта "Вакансии" на java и camunda

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

Порт приложения 8080

Rest:

````shell
/api/echo/MESSAGE_ECHO
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
 
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

http POST :8080/api/vacancy/find < src/resources/VacancyCriterySearchByName.json

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