package ru.perm.v.vacancy_j.rest.validator;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorVacancyDtoTest {
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

        assertEquals(1, errors.size());
        assertEquals("field: title, error: Длина должна быть больше 5 символов.\n", errors.get(0));
    }

    @Test
    void validateNotNullDesciption() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setDescription(null);
        dto.setTitle("1234567890");

        List<String> errors = validator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals("field: description, error: не должно равняться null\n", errors.get(0));
    }

    @Test
    void validateNotNullCompany() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(null);
        dto.setTitle("1234567890");

        List<String> errors = validator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals("field: company, error: не должно равняться null\n", errors.get(0));
    }

    @Test
    void validateNotNullStatus() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(new CompanyDto());
        dto.setTitle("1234567890");
        dto.setStatus(null);
        List<String> errors = validator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals("field: status, error: не должно равняться null\n", errors.get(0));
    }

    @Test
    void validateNotNullDateChanged() {
        ValidatorVacancyDto validator = new ValidatorVacancyDto();
        VacancyDto dto = new VacancyDto();
        dto.setCompany(new CompanyDto());
        dto.setTitle("1234567890");
        dto.setStatus("in_plan");
        List<String> errors = validator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals("field: dateChanged, error: не должно быть пустым\n", errors.get(0));
    }

}
