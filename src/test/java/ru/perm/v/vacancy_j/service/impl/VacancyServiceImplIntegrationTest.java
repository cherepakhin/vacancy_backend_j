package ru.perm.v.vacancy_j.service.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.entity.VacancySort;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
public class VacancyServiceImplIntegrationTest {
    @Autowired
    private IVacancyRepository vacancyRepository;
    @Autowired
    private ICompanyRepository companyRepository;

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
    void findByTitle() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        List<VacancyDto> vacancies = vacancyService.findByTitle("VACANCY 1 COMPANY 1");

        assertEquals(1, vacancies.size());

        CompanyDto companyDto = new CompanyDto(1L,"Company 1");
        VacancyDto vacancy = new VacancyDto(1L, "Vacancy 1 Company 1", "Description Vacancy 1 Company 1",
                companyDto, "https://v.perm.ru/", "Comment 1", "in_plan", "01.08.2025");
        assertEquals(companyDto, vacancies.get(0).getCompany());
        assertEquals(vacancy, vacancies.get(0));
    }

    @Test
    void findByCriteryWithLikeName() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        VacancyCriterySearch criterySearch = new VacancyCriterySearch();
        criterySearch.setByTitle("%1");

        List<VacancyDto> vacancies = vacancyService.findByCritery(criterySearch);

        assertEquals(2, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN());
        assertEquals(2L, vacancies.get(1).getN());
    }

    @Test
    void findByCriteryWithEqTitle() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        VacancyCriterySearch criterySearch = new VacancyCriterySearch();
        criterySearch.setByTitle("Vacancy 1 Company 1");

        List<VacancyDto> vacancies = vacancyService.findByCritery(criterySearch);

        assertEquals(1, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN());
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
    void findByCriteryWithInNNAndLikeTitle() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        VacancyCriterySearch criterySearch = new VacancyCriterySearch();
        criterySearch.setNn(List.of(1L, 3L));
        criterySearch.setByTitle("%Company 1");
        List<VacancyDto> vacancies = vacancyService.findByCritery(criterySearch);

        assertEquals(1, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN());
    }

    @Test
    void getAllSortByN() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        List<VacancyDto> vacancies = null;
        try {
            vacancies = vacancyService.getAll(VacancySort.N);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(4, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN());
        assertEquals(2L, vacancies.get(1).getN());
        assertEquals(3L, vacancies.get(2).getN());
        assertEquals(4L, vacancies.get(3).getN());
    }

    @Test
    void getAllSortByTitle() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        List<VacancyDto> vacancies = null;
        try {
            vacancies = vacancyService.getAll(VacancySort.TITLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(4, vacancies.size());
        assertEquals(1L, vacancies.get(0).getN()); // Vacancy 1 Company 1
        assertEquals(3L, vacancies.get(1).getN()); // Vacancy 1 Company 2
        assertEquals(2L, vacancies.get(2).getN()); // Vacancy 2 Company 1
        assertEquals(4L, vacancies.get(3).getN()); // Vacancy 2 Company 2
    }

    @Test
    void create() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        VacancyEntity vacancyEntity = new VacancyEntity();
        Long n = vacancyService.getNextMaxN();
        vacancyEntity.setN(n);
        vacancyEntity.setTitle("TEST TITLE");
        vacancyEntity.setDescription("TEST DESCRIPTION(");
        List<CompanyEntity> companies = companyRepository.findByN(1L);
        vacancyEntity.setCompanyEntity(companies.get(0));

        VacancyEntity saved = vacancyRepository.save(vacancyEntity);

        assertEquals(n, saved.getN());
    }
}
