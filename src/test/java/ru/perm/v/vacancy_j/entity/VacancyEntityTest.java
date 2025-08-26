package ru.perm.v.vacancy_j.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VacancyEntityTest {

    @Test
    void getIdNotNull() {
        VacancyEntity vacancyEntity = new VacancyEntity();
        assertNotNull(vacancyEntity.getN());
    }

    @Test
    void getId() {
        VacancyEntity vacancyEntity = new VacancyEntity();
        vacancyEntity.setN(10L);
        assertEquals(10L, vacancyEntity.getN());
    }

    @Test
    void getTitle() {
        VacancyEntity vacancyEntity = new VacancyEntity();
        vacancyEntity.setTitle("test");

        assertEquals("test", vacancyEntity.getTitle());
    }

    @Test
    void testEquals() {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setN(10L);

        VacancyEntity vacancyEntity1 = new VacancyEntity();
        vacancyEntity1.setN(10L);
        vacancyEntity1.setTitle("test");
        vacancyEntity1.setCompanyEntity(companyEntity);

        VacancyEntity vacancyEntity2 = new VacancyEntity();
        vacancyEntity2.setN(10L);
        vacancyEntity2.setTitle("test");
        vacancyEntity2.setCompanyEntity(companyEntity);

        assertEquals(vacancyEntity1, vacancyEntity2);
    }

    @Test
    void testEqualsSelf() {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setN(10L);

        VacancyEntity vacancyEntity1 = new VacancyEntity();
        vacancyEntity1.setN(10L);
        vacancyEntity1.setTitle("test");
        vacancyEntity1.setCompanyEntity(companyEntity);

        assertEquals(vacancyEntity1, vacancyEntity1);
    }

    @Test
    void createDefault() {
        VacancyEntity vacancyEntity = new VacancyEntity();

        assertNotNull(vacancyEntity);
        assertEquals(-1L, vacancyEntity.getN());
        assertEquals("", vacancyEntity.getTitle());
        assertEquals("", vacancyEntity.getDescription());
        assertEquals(new CompanyEntity(-1L,""), vacancyEntity.getCompanyEntity());
        assertEquals(LocalDate.of(1970, 1, 1), vacancyEntity.getDateChanged());
    }

    @Test
    void checkHashCode() {
        VacancyEntity vacancyEntity = new VacancyEntity();
        vacancyEntity.setN(10L);
        vacancyEntity.setTitle("test");

        VacancyEntity vacancyEntity1 = new VacancyEntity();
        vacancyEntity1.setN(10L);
        vacancyEntity1.setTitle("test");

        assertEquals(vacancyEntity.hashCode(), vacancyEntity1.hashCode());
    }
}