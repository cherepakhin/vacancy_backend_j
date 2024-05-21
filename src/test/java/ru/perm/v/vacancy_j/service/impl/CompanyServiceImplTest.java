package ru.perm.v.vacancy_j.service.impl;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyServiceImplTest {
    ICompanyRepository companyRepository = mock(ICompanyRepository.class);

    @Test
    void getByN() {
        CompanyEntity companyEntity = new CompanyEntity(100L, "NAME_100");
        when(companyRepository.findByN(100L)).thenReturn(List.of(companyEntity));
        CompanyService companyService = new CompanyServiceImpl(companyRepository);

        CompanyDto receivedDto = companyService.getByN(100L);

        assertNotNull(receivedDto);
        assertEquals(new CompanyDto(100L, "NAME_100"), receivedDto);
    }
}