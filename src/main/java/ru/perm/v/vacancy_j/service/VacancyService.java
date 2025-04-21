package ru.perm.v.vacancy_j.service;

import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    VacancyDto getByN(Long n) throws Exception;
    List<VacancyDto> getAll();
    List<VacancyDto> findByName(String name);
}
