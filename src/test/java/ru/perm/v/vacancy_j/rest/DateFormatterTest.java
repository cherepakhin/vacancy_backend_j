package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.mapper.DateFormatter;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateFormatterTest {

    @Test
    void validDate() {
        String stringDate = "31.12.2021";

        LocalDate ddate = DateFormatter.fromString(stringDate);

        assertEquals(LocalDate.of(2021, 12, 31), ddate);
    }

    @Test
    void invalidDate() {
        String stringDate = "32.12.2021";
        Exception exception = assertThrows(Exception.class, () -> DateFormatter.fromString(stringDate));

        assertEquals("Text '32.12.2021' could not be parsed: Invalid value for DayOfMonth (valid values 1 - 28/31): 32", exception.getMessage());
    }
    @Test
    void invalidEmptyDate() {
        String stringDate = "";
        Exception exception = assertThrows(Exception.class, () -> DateFormatter.fromString(stringDate));

        assertEquals("Text '' could not be parsed at index 0", exception.getMessage());
    }
}
