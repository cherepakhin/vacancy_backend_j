package ru.perm.v.vacancy_j.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyEntityTest {

    @Test
    void getId() {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setN(10L);

        assertEquals(10L, companyEntity.getN());
    }

    @Test
    void getName() {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName("test");

        assertEquals("test", companyEntity.getName());
    }

    @Test
    void testEquals() {
        CompanyEntity companyEntity1 = new CompanyEntity();
        companyEntity1.setN(10L);
        companyEntity1.setName("test");

        CompanyEntity companyEntity2 = new CompanyEntity();
        companyEntity2.setN(10L);
        companyEntity2.setName("test");

        assertEquals(companyEntity1, companyEntity2);
    }
}