package ru.perm.v.vacancy_j.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VacancyServiceImplTest {
    IVacancyRepository vacancyRepository = mock(IVacancyRepository.class);

    @Test
    void getByN() {
        VacancyEntity vacancyEntity = new VacancyEntity();
        vacancyEntity.setN(100L);
        vacancyEntity.setTitle("TITLE");
        vacancyEntity.setDescription("DESCRIPTION");
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setN(10L);
        companyEntity.setName("COMPANY");
        vacancyEntity.setCompanyEntity(companyEntity);

        when(vacancyRepository.findByN(100L)).thenReturn(Collections.singletonList(vacancyEntity));
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        VacancyDto foundVacancy = null;
        try {
            foundVacancy = vacancyService.getByN(100L);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(foundVacancy);
        assertEquals(100L, foundVacancy.getN());
        assertEquals("TITLE", foundVacancy.getTitle());
        assertEquals("DESCRIPTION", foundVacancy.getDescription());
        assertEquals(new CompanyDto(10L, "COMPANY"), foundVacancy.getCompany());
   }

    @Test
    void getAll() {
        VacancyEntity vacancyEntity100 = new VacancyEntity();
        vacancyEntity100.setN(100L);
        vacancyEntity100.setTitle("TITLE 100");
        vacancyEntity100.setDescription("DESCRIPTION 100");
        vacancyEntity100.setLink("SOURCE 100");
        vacancyEntity100.setComment("COMMENT 100");

        CompanyEntity companyEntity10 = new CompanyEntity();
        companyEntity10.setN(10L);
        companyEntity10.setName("COMPANY 10");
        vacancyEntity100.setCompanyEntity(companyEntity10);

        VacancyEntity vacancyEntity200 = new VacancyEntity();
        vacancyEntity200.setN(200L);
        vacancyEntity200.setTitle("TITLE 200");
        vacancyEntity200.setDescription("DESCRIPTION 200");
        vacancyEntity200.setLink("SOURCE 200");
        CompanyEntity companyEntity20 = new CompanyEntity();
        companyEntity20.setN(20L);
        companyEntity20.setName("COMPANY 20");
        vacancyEntity200.setCompanyEntity(companyEntity20);

        when(vacancyRepository.findAll()).thenReturn(List.of(vacancyEntity100, vacancyEntity200));

        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        List<VacancyDto> dtos = vacancyService.getAll();

        assertEquals(2, dtos.size());
        CompanyDto companyDto10 = new CompanyDto(10L, "COMPANY 10");
        CompanyDto companyDto20 = new CompanyDto(20L, "COMPANY 20");
        assertEquals(2, dtos.size());
        assertEquals(new VacancyDto(100L, "TITLE 100", "DESCRIPTION 100", companyDto10, "SOURCE 100", "COMMENT 100"), dtos.get(0));
    }

    @Test
    void findByName() {
        String SEARCH_TITLE = "SEARCH_TITLE";

        VacancyEntity vacancyEntity100 = new VacancyEntity();
        vacancyEntity100.setN(100L);
        vacancyEntity100.setTitle(SEARCH_TITLE);

        VacancyEntity query = new VacancyEntity();
        query.setTitle(SEARCH_TITLE);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("n", "companyEntity", "description", "link", "comment")
                .withIncludeNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<VacancyEntity> example = Example.of(query, matcher);

        when(vacancyRepository.findAll(example)).thenReturn(List.of(vacancyEntity100));

        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        List<VacancyDto> dtos = vacancyService.findByName(SEARCH_TITLE);

        assertEquals(1, dtos.size());
        assertEquals(100L, dtos.get(0).getN());
        assertEquals(SEARCH_TITLE, dtos.get(0).getTitle());
    }
}
