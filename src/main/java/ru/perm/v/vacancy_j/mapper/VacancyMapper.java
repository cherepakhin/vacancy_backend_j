package ru.perm.v.vacancy_j.mapper;

import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.VacancyEntity;

public class VacancyMapper implements IMapper<VacancyDto, VacancyEntity> {

    CompanyMapper companyMapper = new CompanyMapper();
    @Override
    public VacancyDto toDto(VacancyEntity entity) {
        VacancyDto dto = new VacancyDto();
        dto.setN(entity.getN());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        //TODO: add source
        dto.setSource("");
        dto.setComment("TODO");
        CompanyDto companyDto = companyMapper.toDto(entity.getCompanyEntity());
        dto.setCompany(companyDto);

        return dto;
    }

    @Override
    public VacancyEntity toEntity(VacancyDto vacancyDto) {
        VacancyEntity vacancyEntity = new VacancyEntity();
        vacancyEntity.setN(vacancyDto.getN());
        vacancyEntity.setTitle(vacancyDto.getTitle());
        vacancyEntity.setDescription(vacancyDto.getDescription());
        vacancyEntity.setCompanyEntity(companyMapper.toEntity(vacancyDto.getCompany()));
        return vacancyEntity;
    }
}
