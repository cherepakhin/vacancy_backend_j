package ru.perm.v.vacancy_j.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.entity.VacancySort;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VacancyServiceImplMockTest {
    @InjectMocks
    private VacancyServiceImpl vacancyService;

    @Mock
    private VacancyServiceImpl vacancyServiceMock;

    @Mock
    IVacancyRepository vacancyRepository;

    @Test
    void geAll() {
        VacancyEntity vacancyEntity100 = new VacancyEntity();
        vacancyEntity100.setN(100L);
        CompanyEntity companyEntity10 = new CompanyEntity();
        companyEntity10.setN(10L);
        vacancyEntity100.setCompanyEntity(companyEntity10);

        VacancyEntity vacancyEntity200 = new VacancyEntity();
        vacancyEntity200.setN(200L);
        CompanyEntity companyEntity20 = new CompanyEntity();
        companyEntity20.setN(20L);
        vacancyEntity200.setCompanyEntity(companyEntity20);

        VacancyDto vacancyDto100 = new VacancyDto();
        vacancyDto100.setN(100L);
        VacancyDto vacancyDto200 = new VacancyDto();
        vacancyDto200.setN(200L);

        when(vacancyRepository.findAll(Sort.by(VacancySort.N))).thenReturn(List.of(vacancyEntity100, vacancyEntity200));

        List<VacancyDto> vacancies = vacancyService.getAll();

        assertEquals(2, vacancies.size());
        assertEquals(100L, vacancies.get(0).getN());
        assertEquals(200L, vacancies.get(1).getN());

        verify(vacancyRepository).findAll(Sort.by(VacancySort.N));
    }
}
