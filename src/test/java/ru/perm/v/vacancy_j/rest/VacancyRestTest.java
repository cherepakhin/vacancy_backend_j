package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VacancyRestTest {

    @Test
    void getByN_notNull() {
      VacancyRest vacancyRest = new VacancyRest();
      assertNotNull(vacancyRest.getByN(100L));
    }

    @Test
    void getByN() {
        VacancyRest vacancyRest = new VacancyRest();
        assertEquals(100L, vacancyRest.getByN(100L).getN());
    }
}