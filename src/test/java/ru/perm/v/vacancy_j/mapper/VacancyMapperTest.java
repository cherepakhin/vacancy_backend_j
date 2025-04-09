package ru.perm.v.vacancy_j.mapper;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VacancyMapperTest {
    VacancyMapper vacancyMapper = new VacancyMapper();
    @Test
    void toDto() {
        VacancyEntity vacancyEntity = new VacancyEntity();
        Long N = 100L;
        String TITLE = "TITLE";
        vacancyEntity.setN(N);
        vacancyEntity.setTitle(TITLE);

        CompanyEntity companyEntity = new CompanyEntity();
        Long COMPANY_N = 100L;
        String COMPANY_NAME = "COMPANY_NAME";
        companyEntity.setN(COMPANY_N);
        companyEntity.setName(COMPANY_NAME);

        vacancyEntity.setCompanyEntity(companyEntity);

        VacancyDto vacancyDto = vacancyMapper.toDto(vacancyEntity);

        assertEquals(N, vacancyDto.getN());
        assertEquals(TITLE, vacancyDto.getTitle());
        assertEquals(COMPANY_N, vacancyDto.getCompany().getN());
        assertEquals(COMPANY_NAME, vacancyDto.getCompany().getName());
    }

    @Test
    void toEntity() {
        CompanyDto companyDto = new CompanyDto();
        Long COMPANY_N = 100L;
        String COMPANY_NAME = "COMPANY_NAME";
        companyDto.setN(COMPANY_N);
        companyDto.setName(COMPANY_NAME);

        VacancyDto vacancyDto = new VacancyDto();
        Long VACANCY_N = 10L;
        String VACANCY_TITLE = "VACANCY_TITLE";
        String VACANCY_DESCRIPTION = "VACANCY_DESCRIPTION";

        vacancyDto.setN(VACANCY_N);
        vacancyDto.setTitle(VACANCY_TITLE);
        vacancyDto.setDescription(VACANCY_DESCRIPTION);
        vacancyDto.setCompany(companyDto);

        VacancyMapper vacancyMapper = new VacancyMapper();

        VacancyEntity vacancyEntity = vacancyMapper.toEntity(vacancyDto);

        assertEquals(VACANCY_N, vacancyEntity.getN());
        assertEquals(VACANCY_TITLE, vacancyEntity.getTitle());
        assertEquals(VACANCY_DESCRIPTION, vacancyEntity.getDescription());
        assertEquals(COMPANY_N, vacancyEntity.getCompanyEntity().getN());
        assertEquals(COMPANY_NAME, vacancyEntity.getCompanyEntity().getName());
    }
}
