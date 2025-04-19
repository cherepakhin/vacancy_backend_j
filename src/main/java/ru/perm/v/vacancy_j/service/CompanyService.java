package ru.perm.v.vacancy_j.service;

import ru.perm.v.vacancy_j.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    CompanyDto getByN(Long n) throws Exception;
    List<CompanyDto> getAll();
}
