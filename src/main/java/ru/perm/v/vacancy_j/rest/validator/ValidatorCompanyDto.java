package ru.perm.v.vacancy_j.rest.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.perm.v.vacancy_j.dto.CompanyDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidatorCompanyDto {
    public List<String> validate(CompanyDto dto) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.usingContext().getValidator();
        Set<ConstraintViolation<CompanyDto>> validates = validator.validate(dto);
        List<String> ret = new ArrayList<>();
        if (validates.size() > 0) {
//            StringBuilder err = new StringBuilder(String.format("%s. Errors: ", dto.toString()));
            List<ConstraintViolation<CompanyDto>> errors = validates.stream().collect(Collectors.toList());
            for (ConstraintViolation<CompanyDto> validateErr : errors) {
                ret.add(String.format("field: %s, error: %s\n", validateErr.getPropertyPath(), validateErr.getMessage()));
            }
        }
        return ret;
    }
}
