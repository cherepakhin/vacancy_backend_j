package ru.perm.v.vacancy_j.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.perm.v.vacancy_j.dto.VacancyDto;

@RestController
@RequestMapping("/vacancy")
public class VacancyRest {
    @GetMapping("/{id}")
    public VacancyDto getById(@PathVariable String id) {
        return new VacancyDto();
    }
}
