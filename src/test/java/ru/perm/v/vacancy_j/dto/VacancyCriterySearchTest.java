package ru.perm.v.vacancy_j.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VacancyCriterySearchTest {

    @Test
    void testEquals() {
        VacancyCriterySearch critery1 = new VacancyCriterySearch(List.of(1L), "Title");
        VacancyCriterySearch critery2 = new VacancyCriterySearch(List.of(1L), "Title");

        assertEquals(critery1, critery2);
    }

    @Test
    void testHashCode() {
        VacancyCriterySearch critery1 = new VacancyCriterySearch(List.of(1L), "Title");
        VacancyCriterySearch critery2 = new VacancyCriterySearch(List.of(1L), "Title");

        assertEquals(critery1.hashCode(), critery2.hashCode());
    }

    @Test
    void testNotEqual() {
        VacancyCriterySearch critery1 = new VacancyCriterySearch(List.of(1L), "Title");
        VacancyCriterySearch critery2 = new VacancyCriterySearch(List.of(2L), "Title");

        assertNotEquals(critery1, critery2);
    }
}