package ru.perm.v.vacancy_j.rest.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.perm.v.vacancy_j.dto.VacancyDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidatorVacancyDto {
    public static List<String> validate(VacancyDto dto) {
        if (dto == null) {
            return List.of("dto is null");
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.usingContext().getValidator();
        Set<ConstraintViolation<VacancyDto>> validates = validator.validate(dto);
        List<String> ret = new ArrayList<>();
        if (validates.size() > 0) {
//            StringBuilder err = new StringBuilder(String.format("%s. Errors: ", dto.toString()));
            List<ConstraintViolation<VacancyDto>> errors = validates.stream().collect(Collectors.toList());
            for (ConstraintViolation<VacancyDto> validateErr : errors) {
                ret.add(String.format("field: %s, error: %s\n", validateErr.getPropertyPath(), validateErr.getMessage()));
            }
        }
        if (!isValidStatus(dto.getStatus())) {
            ret.add(String.format(
                    "field: status, error: %s\n",
                    "status must be in_plan, in_work or deleted"
            ));
        }
        return ret;
    }

    protected static boolean isValidStatus(String status) {
        if (status == null) {
            return false;
        }
        return List.of("in_plan", "in_work", "deleted").contains(status);
    }
}
