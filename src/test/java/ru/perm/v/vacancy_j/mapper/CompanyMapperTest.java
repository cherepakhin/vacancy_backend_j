package ru.perm.v.vacancy_j.mapper;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;

import static org.junit.jupiter.api.Assertions.*;

class CompanyMapperTest {

    @Test
    void toDto() {
        CompanyEntity entity = new CompanyEntity();
        entity.setN(1L);
        entity.setName("name");

        CompanyMapper companyMapper = new CompanyMapper();
        CompanyDto dto = companyMapper.toDto(entity);

        assertEquals(entity.getN(), dto.getN());
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    void toEntity() {
        CompanyDto dto = new CompanyDto();
        dto.setN(1L);
        dto.setName("name");

        CompanyMapper companyMapper = new CompanyMapper();
        CompanyEntity entity = companyMapper.toEntity(dto);

        assertEquals(dto.getN(), entity.getN());
        assertEquals(dto.getName(), entity.getName());
    }
}