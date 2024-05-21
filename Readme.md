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
 