package ru.perm.v.vacancy_j.service.impl;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {
    ICompanyRepository companyRepository = mock(ICompanyRepository.class);

    @Test
    void getByN() {
        CompanyEntity companyEntity = new CompanyEntity(100L, "NAME_100");
        when(companyRepository.findByN(100L)).thenReturn(List.of(companyEntity));
        CompanyService companyService = new CompanyServiceImpl(companyRepository);

        CompanyDto receivedDto = null;
        try {
            receivedDto = companyService.getByN(100L);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(receivedDto);
        assertEquals(new CompanyDto(100L, "NAME_100"), receivedDto);
    }

//    @Test
//    void getByN_with_ExceptionNotFound() {
//        doThrow(new RuntimeException()).when(companyRepository).findByN(100L);
//        CompanyService companyService = new CompanyServiceImpl(companyRepository);
//        try {
//            companyService.getByN(100L);
//        } catch (Exception e) {
//        }
//        fail();
//
//
//    }

}