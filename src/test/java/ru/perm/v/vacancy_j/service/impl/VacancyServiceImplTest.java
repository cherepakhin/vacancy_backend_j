package ru.perm.v.vacancy_j.service.impl;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.Collections;

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
        assertEquals("COMPANY", foundVacancy.getCompany());
        assertFalse(foundVacancy.getCompleted());
    }
}
