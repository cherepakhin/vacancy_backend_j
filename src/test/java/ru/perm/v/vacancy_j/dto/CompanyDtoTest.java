package ru.perm.v.vacancy_j.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompanyDtoTest {

    @Test
    void setName() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("test");
        assertEquals("test", companyDto.getName());
    }

    @Test
    void testEquals() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("test");
        CompanyDto companyDto1 = new CompanyDto();
        companyDto1.setName("test");
        assertEquals(companyDto, companyDto1);
    }

    @Test
    void testConstructor() {
        CompanyDto companyDto = new CompanyDto();
        assertEquals("", companyDto.getName());
        assertEquals(-1L, companyDto.getN());
    }

    @Test
    void validEmptyName() {
        CompanyDto companyDto = new CompanyDto(1L, "");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CompanyDto>> violations = validator.validate(companyDto);
        if (violations.isEmpty()) {
            fail();
        }

        List<ConstraintViolation<CompanyDto>> listViolations = violations.stream().toList();

        assertEquals(1, listViolations.size());

        ConstraintViolation<CompanyDto> violation0 = listViolations.get(0);

        assertEquals("name", violation0.getPropertyPath().toString());
        assertEquals("Длина name в CompanyDto должна быть больше 5 символов.", violation0.getMessage());
    }

    @Test
    void notValidShortName() {
        CompanyDto companyDto = new CompanyDto(1L, "123");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CompanyDto>> violations = validator.validate(companyDto);
        if (violations.isEmpty()) {
            fail();
        }

        List<ConstraintViolation<CompanyDto>> listViolations = violations.stream().toList();

        assertEquals(1, listViolations.size());

        ConstraintViolation<CompanyDto> violation = listViolations.get(0);

        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("Длина name в CompanyDto должна быть больше 5 символов.", violation.getMessage());
    }

    @Test
    void notValidNullName() {
        CompanyDto companyDto = new CompanyDto(1L, null);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CompanyDto>> violations = validator.validate(companyDto);
        if (violations.isEmpty()) {
            fail();
        }

        List<ConstraintViolation<CompanyDto>> listViolations = violations.stream().toList();

        assertEquals(1, listViolations.size());

        ConstraintViolation<CompanyDto> violation = listViolations.get(0);

        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("name не может быть null.", violation.getMessage());
    }

}