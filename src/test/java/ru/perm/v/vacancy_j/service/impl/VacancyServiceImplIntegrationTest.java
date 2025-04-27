package ru.perm.v.vacancy_j.service.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.VacancySort;
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
        List<VacancyDto> vacancies = vacancyService.getAll();
        assertEquals(4, vacancies.size());
    }

    @Test
    void getAllSortbyN() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        List<VacancyDto> vacancies = vacancyService.getAll();
        assertEquals(4, vacancies.size());
    }

    @Test
    void findByName() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        List<VacancyDto> vacancies = vacancyService.findByName("Vacancy 1 Company 1");

        assertEquals(1, vacancies.size());

        CompanyDto companyDto = new CompanyDto(1L,"Company 1");
        VacancyDto vacancy = new VacancyDto(1L, "Vacancy 1 Company 1",
                "Description Vacancy 1 Company 1", companyDto, "", "");
        assertEquals(vacancy, vacancies.get(0));
    }

    @Test
    void findByCriteryWithLikeName() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        VacancyCriterySearch criterySearch = new VacancyCriterySearch();
        criterySearch.setByName("%1");

        List<VacancyDto> vacancies = vacancyService.findByCritery(criterySearch);

        assertEquals(2, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN());
        assertEquals(2L, vacancies.get(1).getN());
    }

    @Test
    void findByCriteryWithEqName() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        VacancyCriterySearch criterySearch = new VacancyCriterySearch();
        criterySearch.setByName("Vacancy 1 Company 2");

        List<VacancyDto> vacancies = vacancyService.findByCritery(criterySearch);

        assertEquals(1, vacancies.size());
        assertEquals(3L, vacancies.get(0).getN());
    }

    @Test
    void findByCriteryWithInNN() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        VacancyCriterySearch criterySearch = new VacancyCriterySearch();
        criterySearch.setNn(List.of(1L, 3L));

        List<VacancyDto> vacancies = vacancyService.findByCritery(criterySearch);

        assertEquals(2, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN());
        assertEquals(3L, vacancies.get(1).getN());
    }

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

    @Test
    void getAllSortByN() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        List<VacancyDto> vacancies = vacancyService.getAll(VacancySort.N);

        assertEquals(4, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN());
        assertEquals(2L, vacancies.get(1).getN());
        assertEquals(3L, vacancies.get(2).getN());
        assertEquals(4L, vacancies.get(3).getN());
    }

    @Test
    void getAllSortByTitle() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        List<VacancyDto> vacancies = vacancyService.getAll(VacancySort.TITLE);

        assertEquals(4, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN()); // Vacancy 1 Company 1
        assertEquals(3L, vacancies.get(1).getN()); // Vacancy 1 Company 2
        assertEquals(2L, vacancies.get(2).getN()); // Vacancy 2 Company 1
        assertEquals(4L, vacancies.get(3).getN()); // Vacancy 2 Company 2
    }
}
