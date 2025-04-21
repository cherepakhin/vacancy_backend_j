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