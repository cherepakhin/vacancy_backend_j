package ru.perm.v.vacancy_j.rest.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.perm.v.vacancy_j.dto.CompanyDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidatorCompanyDto {
    public List<String> validate(CompanyDto dto) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.usingContext().getValidator();
        Set<ConstraintViolation<CompanyDto>> validates = validator.validate(dto);
        List<String> ret = new ArrayList<>();
        if (!validates.isEmpty()) {
            List<ConstraintViolation<CompanyDto>> errors = validates.stream().toList();
            for (ConstraintViolation<CompanyDto> validateErr : errors) {
                ret.add("field: " + validateErr.getPropertyPath() + ", error: " +
                        validateErr.getMessage() + "\n");
            }
        }
        return ret;
    }
}
