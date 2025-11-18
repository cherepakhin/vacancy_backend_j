package ru.perm.v.vacancy_j.rest.validator;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorVacancyDtoTest {
    @Test
    void validateEmptyTitle() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setTitle("");

        List<String> errors = validator.validate(dto);

        assertTrue(errors.contains("field: title, error: Длина должна быть больше 5 символов.\n"));
    }

    @Test
    void validateShortTitle() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setTitle("123");

        List<String> errors = validator.validate(dto);

        assertTrue(errors.contains("field: title, error: Длина должна быть больше 5 символов.\n"));
    }

    @Test
    void validateNotNullDescription() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setTitle("1234567890");
        dto.setStatus("in_plan");
        dto.setDescription(null);
        dto.setDateChanged("2020-01-01");

        List<String> errors = validator.validate(dto);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("field: description, error: must not be empty\n"));
    }

    @Test
    void validateNotNullCompany() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(null);

        List<String> errors = validator.validate(dto);

        assertTrue(errors.contains("field: company, error: must not be null\n"));
    }

    @Test
    void validateNotNullStatus() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(new CompanyDto());
        dto.setDescription("Description");
        dto.setTitle("1234567890");
        dto.setDateChanged("2020-01-01");
        dto.setStatus(null);
        List<String> errors = validator.validate(dto);

        assertEquals(2, errors.size());
        assertEquals("field: status, error: must not be empty\n", errors.get(0));
        assertEquals("field: status, error: status must be in_plan, in_work or deleted\n", errors.get(1));
    }

    @Test
    void validateNotNullDateChanged() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(new CompanyDto());
        dto.setTitle("1234567890");
        dto.setStatus("in_plan");
        dto.setDescription("Description");
        List<String> errors = validator.validate(dto);

        assertTrue(errors.contains("field: dateChanged, error: must not be empty\n"));
    }

    @Test
    void notValidStatus() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setStatus("1234567890");
        dto.setDescription("Description");
        dto.setTitle("1234567890");
        dto.setDateChanged("2020-01-01");
        List<String> errors = validator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals("field: status, error: status must be in_plan, in_work or deleted\n", errors.get(0));
    }
}
