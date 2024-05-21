package ru.perm.v.vacancy_j.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyDtoTest {

    @Test
    void setName() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("test");
        assertEquals("test", companyDto.getName());
    }

    @Test
    void testEquals() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("test");
        CompanyDto companyDto1 = new CompanyDto();
        companyDto1.setName("test");
        assertEquals(companyDto, companyDto1);
    }

    @Test
    void testConstructor() {
        CompanyDto companyDto = new CompanyDto();
        assertEquals("", companyDto.getName());
        assertEquals(-1L, companyDto.getId());
    }
}