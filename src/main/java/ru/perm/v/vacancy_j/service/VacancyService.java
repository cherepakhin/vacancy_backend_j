package ru.perm.v.vacancy_j.service;

import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.VacancySort;

import java.util.List;

public interface VacancyService {
    VacancyDto getByN(Long n) throws Exception;
    List<VacancyDto> getAll();
    List<VacancyDto> getAll(String sortColumn) throws Exception;
    List<VacancyDto> getAll(VacancySort vacancySort);
    List<VacancyDto> findByCritery(VacancyCriterySearch criterySearch);
    VacancyDto update(VacancyDto vacancyDto) throws Exception;
    void deleteByN(Long n) throws Exception;
    VacancyDto create(VacancyDto vacancyDto) throws Exception;
    public Long getNextMaxN();
    List<VacancyDto> findByTitle(String s);
}
