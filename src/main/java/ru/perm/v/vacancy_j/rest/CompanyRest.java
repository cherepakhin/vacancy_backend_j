package ru.perm.v.vacancy_j.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.perm.v.vacancy_j.dto.CompanyDto;

@RestController
@RequestMapping("/company")
public class CompanyRest {
    @GetMapping("/{n}")
    public CompanyDto getByN(@PathVariable Long n) {
        //TODO: receive from service
        CompanyDto companyDto = new CompanyDto();
        companyDto.setN(n);
        return companyDto;
    }
}
