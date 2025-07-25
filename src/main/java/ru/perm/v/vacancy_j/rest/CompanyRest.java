package ru.perm.v.vacancy_j.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")

@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
@Tag(name = "Company REST controller", description = "Контроллер для работы с компаниями")
// Tag для группировки (на пример все GET запросы в одной секции)
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

    @GetMapping("/")
    @Operation(summary = "Получить компанию по N",
            description = "Получить компанию по идентификатору N"
    )
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(companyService.getAll());
    }

    @GetMapping("/{n}")
    @Operation(summary = "Получить компанию по N",
            description = "Получить компанию по идентификатору N"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyDto.class))}
            ),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<?> getByN(
            @Parameter(description = "Номер компании", required = true)
            @Validated @Min(-1)
            @PathVariable Long n) {
        log.info(format("Get Company with n=%s", n));
        try {
            CompanyDto company = companyService.getByN(n);
            return ResponseEntity.ok(company);
        } catch (Exception e) {
            String error = format("Company with n=%s not found.", n);
            log.error(error);
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody CompanyDto companyDto) {
        String message = format("Create %s", companyDto);
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
        String message = format("Company update n=%s %s", n, companyDto);
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
