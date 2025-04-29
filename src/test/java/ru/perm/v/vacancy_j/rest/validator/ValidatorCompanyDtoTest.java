package ru.perm.v.vacancy_j.rest.validator;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorCompanyDtoTest {
    @Test
    void validateShortName() {
        ValidatorCompanyDto validator = new ValidatorCompanyDto();
        CompanyDto dto = new CompanyDto();
        dto.setName("");
        List<String> errors = validator.validate(dto);
        assertTrue(errors.size() == 1);
        assertEquals("field: name, error: Длина name в CompanyDto должна быть больше 5 символов.\n", errors.get(0));
    }
}
