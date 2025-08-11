package ru.perm.v.vacancy_j.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.entity.VacancySort;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        vacancyEntity100.setStatus("STATUS 100");

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
        vacancyEntity200.setStatus("STATUS 200");
        vacancyEntity200.setComment("COMMENT 200");

        when(vacancyRepository.findAll(Sort.by(Sort.Order.asc(VacancySort.N)))).thenReturn(List.of(vacancyEntity100, vacancyEntity200));

        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        List<VacancyDto> dtos = vacancyService.getAll();

        assertEquals(2, dtos.size());
        CompanyDto companyDto10 = new CompanyDto(10L, "COMPANY 10");
        CompanyDto companyDto20 = new CompanyDto(20L, "COMPANY 20");
        assertEquals(2, dtos.size());
        assertEquals(new VacancyDto(
                100L,
                "TITLE 100",
                "DESCRIPTION 100",
                companyDto10,
                "SOURCE 100",
                "COMMENT 100",
                "STATUS 100",
                "01.01.1970"), dtos.get(0));
        assertEquals(new VacancyDto(
                200L,
                "TITLE 200",
                "DESCRIPTION 200",
                companyDto20,
                "SOURCE 200",
                "COMMENT 200",
                "STATUS 200",
                "01.01.1970"), dtos.get(1));
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

        when(vacancyRepository.findAll(example, Sort.by(Sort.Order.asc("n")))).thenReturn(List.of(vacancyEntity100));

        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        List<VacancyDto> dtos = vacancyService.findByTitle(SEARCH_TITLE);

        assertEquals(1, dtos.size());
        assertEquals(100L, dtos.get(0).getN());
        assertEquals(SEARCH_TITLE, dtos.get(0).getTitle());
    }

    @Test
    void update() {
        CompanyEntity companyEntity10 = new CompanyEntity(10L, "COMPANY 10");
        VacancyEntity vacancyEntity100 = new VacancyEntity(100L, "TITLE 100",
                companyEntity10, "DESCRIPTION 100", "SOURCE 100", "COMMENT 100", "", LocalDate.of(2000, 1, 1));
        when(vacancyRepository.existsById(100L)).thenReturn(true);
        when(vacancyRepository.findById(100L)).thenReturn(Optional.of(vacancyEntity100));
        when(vacancyRepository.save(vacancyEntity100)).thenReturn(vacancyEntity100);
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);

        CompanyDto companyDto = new CompanyDto(10L, "COMPANY 10");
        VacancyDto vacancyDto = new VacancyDto(100L, "TITLE 100", "DESCRIPTION 100",
                companyDto, "SOURCE 100", "COMMENT 100", "", "");

        VacancyDto updatedVacancyDto = null;
        try {
            updatedVacancyDto = vacancyService.update(vacancyDto);
        } catch (Exception e) {
            fail();
        }

        assertEquals(vacancyDto, updatedVacancyDto);
        verify(vacancyRepository, times(1)).existsById(100L);
        verify(vacancyRepository, times(1)).findById(100L);
        verify(vacancyRepository, times(1)).save(vacancyEntity100);
    }

    @Test
    public void deleteForExist() {
        Long N = 100L;
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        when(vacancyRepository.existsById(N)).thenReturn(true);
        try {
            vacancyService.deleteByN(N);
        } catch (Exception e) {
            fail();
        }

        verify(vacancyRepository, times(1)).deleteById(N);
    }

    @Test
    public void deleteForNotExist() {
        Long N = 100L;
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        when(vacancyRepository.existsById(N)).thenReturn(false);
        String err = null;
        try {
            vacancyService.deleteByN(N);
        } catch (Exception e) {
            err = e.getMessage();
        }

        verify(vacancyRepository, never()).deleteById(N);
        assertEquals("VacancyDto with N=100 not exist", err);
    }

    @Test
    void getMaxN() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        long MAX_N_FROM_DB = 1L;
        when(vacancyRepository.getMaxN()).thenReturn(MAX_N_FROM_DB);

        Long nextN = vacancyService.getNextMaxN();

        assertEquals(MAX_N_FROM_DB + 1L, nextN);
    }

    @Test
    void create() {
        VacancyService vacancyService = new VacancyServiceImpl(vacancyRepository);
        long MAX_N_FROM_DB = 1L;
        when(vacancyRepository.getMaxN()).thenReturn(MAX_N_FROM_DB);
        CompanyEntity companyEntity = new CompanyEntity(10L, "COMPANY 10");
        VacancyEntity vacancyEntity = new VacancyEntity(MAX_N_FROM_DB + 1L, "TITLE 100",
                companyEntity, "DESCRIPTION 100", "SOURCE 100",
                "COMMENT 100", "", LocalDate.of(2000, 1, 1));
        when(vacancyRepository.save(vacancyEntity)).thenReturn(vacancyEntity);

        CompanyDto companyDto = new CompanyDto(10L, "COMPANY 10");
        VacancyDto vacancyDto = new VacancyDto(0L, "TITLE 100", "DESCRIPTION 100",
                companyDto, "SOURCE 100", "COMMENT 100","","");

        VacancyDto createdVacancy = null;
        try {
            createdVacancy = vacancyService.create(vacancyDto);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(
                new VacancyDto(MAX_N_FROM_DB + 1L, "TITLE 100", "DESCRIPTION 100",
                        companyDto, "SOURCE 100", "COMMENT 100","", ""),
                createdVacancy
        );

        verify(vacancyRepository, times(1)).getMaxN();
        verify(vacancyRepository, times(1)).save(vacancyEntity);
    }
}
