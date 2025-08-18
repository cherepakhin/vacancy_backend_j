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

        assertEquals(2, errors.size());
        Integer countErrors = 0;
        for (String err : errors) {
            if (err.equals("field: title, error: не должно быть пустым\n") ||
                    err.equals("field: title, error: Длина должна быть больше 5 символов.\n")
            ) {
                countErrors++;
            }
        }
        assertEquals(2, countErrors);
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
        dto.setDescription(null);

        List<String> errors = validator.validate(dto);

        assertTrue(errors.contains("field: description, error: не должно равняться null\n"));
    }

    @Test
    void validateNotNullCompany() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(null);

        List<String> errors = validator.validate(dto);

        assertTrue(errors.contains("field: company, error: не должно равняться null\n"));
    }

    @Test
    void validateNotNullStatus() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(new CompanyDto());
        dto.setTitle("1234567890");
        dto.setStatus(null);
        List<String> errors = validator.validate(dto);

        assertTrue(errors.contains("field: status, error: не должно равняться null\n"));
    }

    @Test
    void validateNotNullDateChanged() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(new CompanyDto());
        dto.setTitle("1234567890");
        dto.setStatus("in_plan");
        List<String> errors = validator.validate(dto);

        assertTrue(errors.contains("field: dateChanged, error: не должно быть пустым\n"));
    }

    @Test
    void notValidStatus() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setStatus("1234567890");
        dto.setTitle("1234567890");
        dto.setDateChanged("2020-01-01");
        List<String> errors = validator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals("field: status, error: status must be in_plan, in_work or deleted\n", errors.get(0));
    }
}
