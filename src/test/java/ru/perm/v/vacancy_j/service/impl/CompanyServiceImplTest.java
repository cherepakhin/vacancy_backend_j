package ru.perm.v.vacancy_j.service.impl;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;
import java.util.Set;

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

        assertEquals(MAX_N + 1L, received);
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
        CompanyDto companyDto = new CompanyDto(0L, "NAME12345");
        when(companyRepository.getMaxN()).thenReturn(200L);
        CompanyEntity companyEntity = new CompanyEntity(201L, "NAME12345");
        when(companyRepository.save(new CompanyEntity(201L, "NAME12345"))).thenReturn(companyEntity);
        CompanyService companyService = new CompanyServiceImpl(companyRepository);

        CompanyDto createdDto = null;
        try {
            createdDto = companyService.create(companyDto);
        } catch (Exception e) {
            fail();
        }

        assertEquals(new CompanyDto(201L, "NAME12345"), createdDto);
    }

    @Test
    void createFor_NULL_Name() {
        CompanyDto companyDto = new CompanyDto(null, null);
        CompanyService companyService = new CompanyServiceImpl(companyRepository);
        boolean wasError = false;
        String errorMessage = "";
        try {
            companyService.create(companyDto);
        } catch (Exception e) {
            wasError = true;
            errorMessage = e.getMessage();
        }

        assertTrue(wasError);
//        assertEquals("name не должно быть пустым", errorMessage);
    }

    @Test
    void createForShortName() {
        CompanyDto companyDto = new CompanyDto(null, "1234");
        CompanyService companyService = new CompanyServiceImpl(companyRepository);
        boolean wasError = false;
        String errorMessage = "";
        try {
            companyService.create(companyDto);
        } catch (Exception e) {
            wasError = true;
            errorMessage = e.getMessage();
        }

        assertTrue(wasError);
        assertEquals("Длина name в CompanyDto должна быть больше 5 символов.", errorMessage);
    }

    @Test
    void exampleSetToString() {
        Set<Integer> set = Set.of(1, 2);
// так тоже работает
//        String s = set.stream().sorted().map(e -> e.toString()).reduce("", String::concat);
        String s = set.stream().sorted().map(Object::toString).reduce("", String::concat);

        assertEquals("12", s);
    }

    @Test
    void updateForExist() {
        CompanyEntity oldCompanyEntity= new CompanyEntity(1L, "OLD_NAME_1");
        when(companyRepository.findByN(1L)).thenReturn(List.of(oldCompanyEntity));


        CompanyService companyService = new CompanyServiceImpl(companyRepository);
        CompanyDto updatedCompanyDto = null;
        CompanyDto companyDto = new CompanyDto(1L, "NEW_NAME_1");

        CompanyEntity savedCompanyEntity = new CompanyEntity(1L, "SAVED_NAME_1");
        when(companyRepository.save(new CompanyEntity(1L,"NEW_NAME_1"))).thenReturn(savedCompanyEntity);
        try {
            updatedCompanyDto = companyService.update(companyDto);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertNotNull(updatedCompanyDto);
        assertEquals(new CompanyDto(1L, "SAVED_NAME_1"), updatedCompanyDto);
    }
}