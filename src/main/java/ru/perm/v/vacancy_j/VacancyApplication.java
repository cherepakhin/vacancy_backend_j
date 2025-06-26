package ru.perm.v.vacancy_j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//TODO: actuator
@SpringBootApplication
@EnableCaching
public class VacancyApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyApplication.class, args);
	}

}
