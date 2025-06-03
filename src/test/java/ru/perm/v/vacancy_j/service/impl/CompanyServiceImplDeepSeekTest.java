package ru.perm.v.vacancy_j.service.impl;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.mapper.CompanyMapper;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplDeepSeekTest {

    @Mock
    private ICompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private CompanyEntity testEntity;
    private CompanyDto testDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        testEntity = new CompanyEntity(1L, "Test Company");
        testDto = new CompanyDto(1L, "Test Company");
    }

    @Test
    void getByN_shouldReturnCompany_whenExists() throws Exception {
        when(companyRepository.findByN(1L)).thenReturn(List.of(testEntity));

        CompanyDto result = companyService.getByN(1L);

        assertNotNull(result);
        assertEquals(1L, result.getN());
        assertEquals("Test Company", result.getName());
        verify(companyRepository, times(1)).findByN(1L);
    }

    @Test
    void getByN_shouldThrowException_whenNotFound() {
        when(companyRepository.findByN(anyLong())).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(Exception.class, () -> {
            companyService.getByN(999L);
        });

        assertEquals("Company N=999 not found", exception.getMessage());
        verify(companyRepository, times(1)).findByN(999L);
    }

    @Test
    void getAll_shouldReturnListOfCompanies() {
        when(companyRepository.findAll()).thenReturn(List.of(testEntity));

        List<CompanyDto> result = companyService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testDto, result.get(0));
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void findByLikeName_shouldReturnFilteredCompanies() {
        when(companyRepository.findByLikeName("Test")).thenReturn(List.of(testEntity));

        List<CompanyDto> result = companyService.findByLikeName("Test");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testDto, result.get(0));
        verify(companyRepository, times(1)).findByLikeName("Test");
    }

    @Test
    void create_shouldSaveNewCompany() throws Exception {
        CompanyDto newDto = new CompanyDto(null, "New Company");
        CompanyEntity newEntity = new CompanyEntity(null, "New Company");
        CompanyEntity savedEntity = new CompanyEntity(2L, "New Company");
        CompanyDto savedDto = new CompanyDto(2L, "New Company");

        when(companyRepository.getMaxN()).thenReturn(1L);
        when(companyRepository.save(any(CompanyEntity.class))).thenReturn(savedEntity);

        CompanyDto result = companyService.create(newDto);

        assertNotNull(result);
        assertEquals(2L, result.getN());
        assertEquals("New Company", result.getName());
        verify(companyRepository, times(1)).save(any(CompanyEntity.class));
    }

    @Test
    void create_shouldThrowException_whenValidationFails() {
        CompanyDto invalidDto = new CompanyDto(null, ""); // Empty name is invalid

        Set<ConstraintViolation<CompanyDto>> violations = validator.validate(invalidDto);
        assertFalse(violations.isEmpty());

        Exception exception = assertThrows(Exception.class, () -> {
            companyService.create(invalidDto);
        });

        assertEquals("Длина name в CompanyDto должна быть больше 5 символов.", exception.getMessage());
    }

    @Test
    void getNextN_shouldReturn1_whenNoCompaniesExist() {
        when(companyRepository.getMaxN()).thenReturn(null);

        Long result = companyService.getNextN();

        assertEquals(1L, result);
        verify(companyRepository, times(1)).getMaxN();
    }

    @Test
    void getNextN_shouldReturnNextNumber_whenCompaniesExist() {
        when(companyRepository.getMaxN()).thenReturn(5L);

        Long result = companyService.getNextN();

        assertEquals(6L, result);
        verify(companyRepository, times(1)).getMaxN();
    }

    @Test
    void update_shouldUpdateExistingCompany() throws Exception {
        CompanyDto updatedDto = new CompanyDto(1L, "Updated Company");
        CompanyEntity updatedEntity = new CompanyEntity(1L, "Updated Company");

        when(companyRepository.findByN(1L)).thenReturn(List.of(testEntity));
        when(companyRepository.save(updatedEntity)).thenReturn(updatedEntity);

        CompanyDto result = companyService.update(updatedDto);

        assertNotNull(result);
        assertEquals(1L, result.getN());
        assertEquals("Updated Company", result.getName());
        verify(companyRepository, times(1)).findByN(1L);
        verify(companyRepository, times(1)).save(updatedEntity);
    }

    @Test
    void update_shouldThrowException_whenCompanyNotFound() {
        CompanyDto nonExistingDto = new CompanyDto(999L, "Non-existing Company");
        when(companyRepository.findByN(999L)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(Exception.class, () -> {
            companyService.update(nonExistingDto);
        });

        assertEquals("Company N=999 not found", exception.getMessage());
        verify(companyRepository, times(1)).findByN(999L);
        verify(companyRepository, never()).save(any());
    }
}
