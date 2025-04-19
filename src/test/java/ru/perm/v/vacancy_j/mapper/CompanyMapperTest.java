package ru.perm.v.vacancy_j.mapper;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void toListDto() {
        CompanyEntity entity1 = new CompanyEntity();
        entity1.setN(1L);
        entity1.setName("name1");
        CompanyEntity entity2 = new CompanyEntity();
        entity2.setN(2L);
        entity2.setName("name2");

        CompanyMapper companyMapper = new CompanyMapper();

        List<CompanyDto> dtos = companyMapper.toListDto(List.of(entity1, entity2));

        assertEquals(2, dtos.size());
        assertEquals(new CompanyDto(1L, "name1"), dtos.get(0));
        assertEquals(new CompanyDto(2L, "name2"), dtos.get(1));
    }

    @Test
    void toListEntities() {
        CompanyDto dto1 = new CompanyDto(1L, "name1");
        CompanyDto dto2 = new CompanyDto(2L, "name2");

        CompanyMapper companyMapper = new CompanyMapper();

        List<CompanyEntity> entites = companyMapper.toListEntities(List.of(dto1, dto2));

        assertEquals(2, entites.size());
        assertEquals(new CompanyEntity(1L, "name1"), entites.get(0));
        assertEquals(new CompanyEntity(2L, "name2"), entites.get(1));
    }
}