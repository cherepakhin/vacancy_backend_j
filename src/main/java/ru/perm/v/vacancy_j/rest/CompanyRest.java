package ru.perm.v.vacancy_j.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")
public class CompanyRest {

    @Autowired
    private CompanyService companyService;
    Logger log = LoggerFactory.getLogger(CompanyRest.class);

    public CompanyRest() {
        super();
    }

    public CompanyRest(@Autowired CompanyService companyService) {
        this();
        this.companyService = companyService;
    }

    @GetMapping("/{n}")
    public ResponseEntity<?> getByN(@PathVariable Long n) {
        try {
            CompanyDto company = companyService.getByN(n);
            return ResponseEntity.ok(company);
        } catch (Exception e) {
            String error = String.format("Company with n=%s not found.", n);
            log.error(error);
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
