package ru.perm.v.vacancy_j.mapper;

import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;

import java.util.List;

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

    List<CompanyDto> toListDto(List<CompanyEntity> entities) {
        List<CompanyDto> dtos = entities.stream().map(this::toDto).toList();
        return dtos;
    }

    List<CompanyEntity> toListEntities(List<CompanyDto> dtos) {
        List<CompanyEntity> entites = dtos.stream().map(this::toEntity).toList();
        return entites;
    }
}
