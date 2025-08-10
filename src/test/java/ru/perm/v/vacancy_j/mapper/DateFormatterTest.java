package ru.perm.v.vacancy_j.mapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateFormatterTest {

    @Test
    void formatDate() {
        LocalDate ddate = LocalDate.of(2021, 12, 31);

        assertEquals("31.12.2021", DateFormatter.toString(ddate));
    }

    @Test
    void parseFromString() {
        assertEquals(LocalDate.of(2021, 12, 31), DateFormatter.fromString("31.12.2021"));
    }
}