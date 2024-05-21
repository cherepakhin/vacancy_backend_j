package ru.perm.v.vacancy_j.service;

import ru.perm.v.vacancy_j.dto.CompanyDto;

public interface CompanyService {
    CompanyDto getByN(Long n);
}
