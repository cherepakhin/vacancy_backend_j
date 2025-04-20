package ru.perm.v.vacancy_j.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;
import java.util.ArrayList;


@RestController
@RequestMapping("/vacancy")
@CrossOrigin(origins = "*")
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
