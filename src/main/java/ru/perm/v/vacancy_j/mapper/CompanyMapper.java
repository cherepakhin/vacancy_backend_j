package ru.perm.v.vacancy_j.mapper;

import org.springframework.context.annotation.Bean;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;

public class CompanyMapper implements IMapper<CompanyDto, CompanyEntity> {
    @Override
    public CompanyDto toDto(CompanyEntity companyEntity) {
        CompanyDto dto = new CompanyDto();
        dto.setN(companyEntity.getN());
        dto.setName(companyEntity.getName());
        return dto;
    }

    @Override
    public CompanyEntity toEntity(CompanyDto companyDto) {
        CompanyEntity entity = new CompanyEntity();
        entity.setN(companyDto.getN());
        entity.setName(companyDto.getName());
        return entity;
    }
}
