package ru.perm.v.vacancy_j.rest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import io.swagger.annotations.*;

import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")

//@Api(value = "CompanyRest" , tags = {"Контроллер для работы с компаниями"})
//@SwaggerDefinition(tags = {
//        @Tag(name = "Company Controller", description = "Контроллер для работы с компаниями")
//})
public class CompanyRest {

    @Autowired
    private CompanyService companyService;

    Logger log = LoggerFactory.getLogger(CompanyRest.class);

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public CompanyRest() {
        super();
    }

    public CompanyRest(@Autowired CompanyService companyService) {
        this();
        this.companyService = companyService;
    }

    @GetMapping("/{n}")
//    @ApiOperation(value = "Получить компанию по N",
//            notes = "Получить компанию по идентификатору N",
//            response = CompanyDto.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = CompanyDto.class),
//            @ApiResponse(code = 404, message = "Ресурс не найден"),
//            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса") })
    public ResponseEntity<?> getByN(
//            @ApiParam(value = "Номер компании", required = true)
            @PathVariable Long n) {
        try {
            CompanyDto company = companyService.getByN(n);
            return ResponseEntity.ok(company);
        } catch (Exception e) {
            String error = String.format("Company with n=%s not found.", n);
            log.error(error);
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody CompanyDto companyDto) {
        String message = String.format("Create %s", companyDto);
        log.info(message);
        try {
            CompanyDto dto = companyService.create(companyDto);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{n}")
    public ResponseEntity<?> update(@PathVariable Long n, @RequestBody CompanyDto companyDto) {
        String message = String.format("Company update n=%s %s", n, companyDto);
        log.info(message);
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<CompanyDto>> violations = validator.validate(companyDto);
        if (!violations.isEmpty()) {
            List<ConstraintViolation<CompanyDto>> listViolations = violations.stream().toList();
            String error = listViolationToString(listViolations);
            return ResponseEntity.internalServerError().body(error);
        }
        try {
            companyService.getByN(n);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        try {
            CompanyDto dto = companyService.update(companyDto);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    private String listViolationToString(List<ConstraintViolation<CompanyDto>> listViolations) {
        return listViolations.stream().map(err -> err.getPropertyPath() + ":" + err.getMessage()).collect(Collectors.joining(","));
    }
}
