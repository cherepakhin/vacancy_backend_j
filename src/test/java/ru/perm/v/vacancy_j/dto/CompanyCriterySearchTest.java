package ru.perm.v.vacancy_j.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyCriterySearchTest {

    @Test
    void create() {
        List<Long> nn = List.of(1L, 2L);
        String name = "NAME";
        CompanyCriterySearch criterySearch = new CompanyCriterySearch(nn, name);

        assertEquals(nn, criterySearch.getNn());
        assertEquals(name, criterySearch.getByName());
    }

    @Test
    void createEmpty() {
        CompanyCriterySearch criterySearch = new CompanyCriterySearch();

        assertEquals(List.of(), criterySearch.getNn());
        assertEquals("", criterySearch.getByName());
    }
}