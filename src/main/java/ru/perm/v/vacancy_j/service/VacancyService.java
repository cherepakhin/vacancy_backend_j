package ru.perm.v.vacancy_j.service;

import ru.perm.v.vacancy_j.dto.VacancyDto;

public interface VacancyService {
    VacancyDto getByN(Long n) throws Exception;
}
