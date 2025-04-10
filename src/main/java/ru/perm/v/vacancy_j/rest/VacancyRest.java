package ru.perm.v.vacancy_j.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/vacancy")
public class VacancyRest {
    @GetMapping("/{n}")
    public VacancyDto getByN(@PathVariable Long n) {
        //TODO: receive from service
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setN(n);
        return vacancyDto;
    }
}
