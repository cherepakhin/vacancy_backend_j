package ru.perm.v.vacancy_j.rest.validator;

import org.junit.jupiter.api.Test;
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

        assertEquals(1, errors.size());
        assertEquals("field: title, error: Длина должна быть больше 5 символов.\n", errors.get(0));
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
}
