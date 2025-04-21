package ru.perm.v.vacancy_j.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;

import static java.lang.String.format;


@RestController
@RequestMapping("/vacancy")
@CrossOrigin(origins = "*")
public class VacancyRest {

    @Autowired
    VacancyService vacancyService;

    Logger log = LoggerFactory.getLogger(VacancyRest.class);


    @GetMapping("/")
    public List<VacancyDto> getAll() {
        log.info("get /vacancy/");
        List<VacancyDto> dtos =vacancyService.getAll();
        return dtos;
    }

    @GetMapping("/{n}")
    public VacancyDto getByN(@PathVariable Long n) {
        log.info(format("get /vacancy/%s", n));
        //TODO: receive from service
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setN(n);
        return vacancyDto;
    }

    @PostMapping("/find")
    public List<VacancyDto> findBy(@RequestBody VacancyCriterySearch vacancyCriterySearch) {
        log.info(format("find by %s", vacancyCriterySearch));
        List<VacancyDto> dtos = vacancyService.findByCritery(vacancyCriterySearch);

        return dtos;
    }

    public VacancyService getVacancyService() {
        return vacancyService;
    }

    public void setVacancyService(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }
}
