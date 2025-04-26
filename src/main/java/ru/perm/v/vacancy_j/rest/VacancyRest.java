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
    public ResponseEntity getByN(@PathVariable Long n) {
        log.info(format("get /vacancy/%s", n));
        try {
            return ResponseEntity.ok(vacancyService.getByN(n));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> update(@RequestBody VacancyDto vacancyDto) {
        log.info(format("POST update vacancyDto %s", vacancyDto));
        try {
            // check for exist
            vacancyService.getByN(vacancyDto.getN());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        try {
            VacancyDto dto = vacancyService.update(vacancyDto);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody VacancyDto vacancyDto) {
        log.info(format("POST update vacancyDto %s", vacancyDto));
        try {
            VacancyDto dto = vacancyService.create(vacancyDto);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/find")
    public ResponseEntity<?> findBy(@RequestBody VacancyCriterySearch vacancyCriterySearch) {
        log.info(format("find by %s", vacancyCriterySearch));

        List<VacancyDto> dtos = vacancyService.findByCritery(vacancyCriterySearch);

        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{n}")
    public ResponseEntity<?> deleteByN(@PathVariable Long n) {
        log.info(format("DELETE vacancy n= %s", n));
        try {
            // check for exist
            vacancyService.getByN(n);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        try {
            getVacancyService().deleteByN(n);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    public VacancyService getVacancyService() {
        return vacancyService;
    }

    public void setVacancyService(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }
}
