package ru.perm.v.vacancy_j.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;
import java.util.ArrayList;


@RestController
@RequestMapping("/vacancy")
public class VacancyRest {

    @Autowired
    VacancyService vacancyService;

    @GetMapping("/")
    public List<VacancyDto> getAll() {
        List<VacancyDto> dtos =vacancyService.getAll();
        return dtos;
    }

    @GetMapping("/{n}")
    public VacancyDto getByN(@PathVariable Long n) {
        //TODO: receive from service
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setN(n);
        return vacancyDto;
    }
}
