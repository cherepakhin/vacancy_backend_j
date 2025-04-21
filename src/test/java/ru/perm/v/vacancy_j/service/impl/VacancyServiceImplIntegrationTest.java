package ru.perm.v.vacancy_j.service.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class VacancyServiceImplIntegrationTest {
    @Autowired
    private IVacancyRepository vacancyRepository;

    @Test
    void getAll() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        List<VacancyDto> vacancies =vacancyService.getAll();
        assertEquals(4, vacancies.size());
    }

    @Test
    void findByName() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        List<VacancyDto> vacancies = vacancyService.findByName("Vacancy 1 Company 1");

        assertEquals(1, vacancies.size());

        CompanyDto companyDto = new CompanyDto(1L,"Company 1");
        VacancyDto vacancy = new VacancyDto(1L, "Vacancy 1 Company 1", "Description Vacancy 1 Company 1", companyDto, "", "");
        assertEquals(vacancy,vacancies.get(0));
    }

    @Test
    void withEntityManager() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);


//        assertEquals(4, vacancies.size());
    }


}
