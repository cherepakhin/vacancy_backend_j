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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.perm.v.vacancy_j.dto.CompanyCriterySearch;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")
@Tag(name = "Company REST controller", description = "Контроллер для работы с компаниями")
// Tag для группировки (на пример все GET запросы в одной секции)
public class CompanyRest {

    @Autowired
    private CompanyService companyService;

    Logger log = LoggerFactory.getLogger(CompanyRest.class);

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private static final String COMPANIES_CACHE = "companies";
    private static final String COMPANY_CACHE = "company";

    public CompanyRest() {
        super();
    }

    public CompanyRest(@Autowired CompanyService companyService) {
        this();
        this.companyService = companyService;
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

    @Cacheable(value = COMPANY_CACHE, key = "#n")
    public ResponseEntity<?> getByN(
            @Parameter(description = "Номер компании", required = true)
// @RequestParam(required = true) - для запросов типа: /users/search?name=John. Здесь другой тип запроса /users/1
            @Validated @Min(-1)
            @PathVariable Long n) {
        log.info("Get Company with n=" + n);
        try {
            CompanyDto company = companyService.getByN(n);
            return ResponseEntity.ok(company);
        } catch (Exception e) {
            log.error(e.getMessage());
            String error = format("Company with n=%s not found.", n);
            log.error(error);
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/")
    @Operation(summary = "Получить все компании",
            description = "Получить список всех компаний"
    )
    @Cacheable(value = COMPANIES_CACHE, sync = true)
    public ResponseEntity<?> getAll() {
        log.info("Get all companies");
        return ResponseEntity.ok(companyService.getAll());
    }

    @PutMapping("/")
    @Operation(summary = "Создать новую компанию",
            description = "Создать новую компанию с параметрами из CompanyDTO"
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
    @Caching(
            put = @CachePut(value = COMPANY_CACHE, key = "#result.body.n"),
            evict = @CacheEvict(value = COMPANIES_CACHE, allEntries = true)
    )
    public ResponseEntity<?> create(
            @Parameter(description = "Описание компании", required = true)
            @RequestBody CompanyDto companyDto) {
        String message = format("Create %s", companyDto);
        log.info(message);
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<CompanyDto>> violations = validator.validate(companyDto);
        if (!violations.isEmpty()) {
            List<ConstraintViolation<CompanyDto>> listViolations = violations.stream().toList();
            String error = listViolationToString(listViolations);
            return ResponseEntity.internalServerError().body(error);
        }
        try {
            CompanyDto dto = companyService.create(companyDto);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{n}")
    @Operation(summary = "Изменить компанию",
            description = "Изменить компанию с N и параметрами из CompanyDTO"
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
    @Caching(
            evict = @CacheEvict(value = COMPANIES_CACHE, allEntries = true)
    )
    @CacheEvict(value = COMPANY_CACHE, key = "#n")
    public ResponseEntity<?> update(
            @Parameter(description = "Id компании", required = true)
            @PathVariable Long n,
            @Parameter(description = "Описание компании", required = true)
            @RequestBody CompanyDto companyDto) {
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

    @DeleteMapping("/{n}")
    @Operation(summary = "Удалить компанию",
            description = "Удалить компанию"
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
    @Caching(
            evict = @CacheEvict(value = COMPANIES_CACHE, allEntries = true)
    )
    @CacheEvict(value = COMPANY_CACHE, key = "#n")
    public ResponseEntity<?> delete(
            @Parameter(description = "Id компании", required = true)
            @PathVariable Long n) {
        try {
            companyService.getByN(n);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        try {
            companyService.delete(n);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok(format("Deleted n=%s", n));
    }

    @PostMapping("/find")
    @Operation(summary = "Найти компании по критериям",
            description = "Найти компании по критериям"
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
    public ResponseEntity<List<CompanyDto>> findByExample(@RequestBody CompanyCriterySearch example) {
        String message = format("Find companies by example: %s", example);
        log.info(message);
        List<CompanyDto> companies = companyService.findByExample(example);
        return ResponseEntity.ok(companies);
    }

    private String listViolationToString(List<ConstraintViolation<CompanyDto>> listViolations) {
        return listViolations.stream().map(err -> err.getPropertyPath() + ": " + err.getMessage()).collect(Collectors.joining(","));
    }
}
