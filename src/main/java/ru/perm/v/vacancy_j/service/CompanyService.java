package ru.perm.v.vacancy_j.service;

import ru.perm.v.vacancy_j.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    CompanyDto getByN(Long n) throws Exception;
    List<CompanyDto> getAll();
    List<CompanyDto> findByLikeName(String name);

    CompanyDto create(CompanyDto companyDto) throws Exception;

    public Long getNextN();

    CompanyDto update(CompanyDto companyDto) throws Exception;
}
