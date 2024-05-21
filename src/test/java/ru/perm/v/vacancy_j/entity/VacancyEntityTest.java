package ru.perm.v.vacancy_j.entity;

import org.junit.jupiter.api.Test;

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
        VacancyEntity vacancyEntity = new VacancyEntity();
        vacancyEntity.setN(10L);
        vacancyEntity.setTitle("test");

        VacancyEntity vacancyEntity1 = new VacancyEntity();
        vacancyEntity1.setN(10L);
        vacancyEntity1.setTitle("test");

        assertEquals(vacancyEntity, vacancyEntity1);
    }

    @Test
    void createDefault() {
        VacancyEntity vacancyEntity = new VacancyEntity();

        assertNotNull(vacancyEntity);
        assertEquals(-1L, vacancyEntity.getN());
        assertEquals("", vacancyEntity.getTitle());
        assertEquals("", vacancyEntity.getDescription());
        assertEquals(new CompanyEntity(-1L,""), vacancyEntity.getCompanyEntity());
    }
}