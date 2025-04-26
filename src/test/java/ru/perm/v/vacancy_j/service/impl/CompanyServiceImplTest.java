package ru.perm.v.vacancy_j.service.impl;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.service.CompanyService;

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

    @Test
    void getByN_with_ExceptionNotFound() {
        doThrow(new RuntimeException("CompanyServiceImpl.getByN. NotFound: 100")).when(companyRepository).findByN(100L);
        CompanyService companyService = new CompanyServiceImpl(companyRepository);
        boolean okTest = false;
        String expectedMessage = "";
        try {
            companyService.getByN(100L);
        } catch (Exception e) {
            okTest = true;
            expectedMessage = e.getMessage();
        }

        assertTrue(okTest);
        assertEquals("CompanyServiceImpl.getByN. NotFound: 100", expectedMessage);
    }

    @Test
    void getNextN() {
        Long MAX_N = 100L;
        when(companyRepository.getMaxN()).thenReturn(MAX_N);
        CompanyService companyService = new CompanyServiceImpl(companyRepository);

        Long received = companyService.getNextN();

        assertEquals(MAX_N+1L, received);
    }

    @Test
    void getNextNForEmptyDatabase() {
        when(companyRepository.getMaxN()).thenReturn(null);
        CompanyService companyService = new CompanyServiceImpl(companyRepository);

        Long received = companyService.getNextN();

        assertEquals(1L, received);
    }

    @Test
    void createForValid() {
        CompanyDto companyDto = new CompanyDto(0L, "NAME");
        when(companyRepository.getMaxN()).thenReturn(200L);
        CompanyEntity companyEntity = new CompanyEntity(201L, "NAME");
        when(companyRepository.save(new CompanyEntity(201L, "NAME"))).thenReturn(companyEntity);
        CompanyService companyService = new CompanyServiceImpl(companyRepository);

        CompanyDto createdDto = companyService.create(companyDto);

        assertEquals(new CompanyDto(201L, "NAME"), createdDto);
    }
}