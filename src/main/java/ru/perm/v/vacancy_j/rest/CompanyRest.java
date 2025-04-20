package ru.perm.v.vacancy_j.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")
public class CompanyRest {

    @Autowired
    private CompanyService companyService;

    public CompanyRest() {
        super();
    }

    public CompanyRest(CompanyService companyService) {
        this();
        this.companyService = companyService;
    }

    @GetMapping("/{n}")
    public CompanyDto getByN(@PathVariable Long n) {
        //TODO: receive from service
        try {
            CompanyDto company = companyService.getByN(n);
            return company;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
