package ru.perm.v.vacancy_j.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;


@RestController
@RequestMapping("/vacancy")
@CrossOrigin(origins = "*")
public class VacancyRest {

    @Autowired
    VacancyService vacancyService;

    Logger log = LoggerFactory.getLogger(VacancyRest.class);


    @GetMapping("/")
    public ResponseEntity<List<VacancyDto>> getAll() {
        log.info("get /vacancy/");
        List<VacancyDto> dtos = vacancyService.getAll();
        return ResponseEntity.of(Optional.ofNullable(dtos));
    }

    @GetMapping("/{n}")
    public VacancyDto getByN(@PathVariable Long n) {
        log.info(format("get /vacancy/%s", n));
        try {
            return vacancyService.getByN(n);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/")
    public VacancyDto update(@RequestBody VacancyDto vacancyDto) {
        log.info(format("POST updateByN /vacancy/%s", vacancyDto.getN()));
        log.info(format("vacancyDto %s", vacancyDto));
        try {
            return vacancyService.update(vacancyDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
