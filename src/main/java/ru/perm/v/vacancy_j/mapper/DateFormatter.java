package ru.perm.v.vacancy_j.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDate fromString(String date) {
        return LocalDate.parse(date, formatter);
    }

    public static String toString(LocalDate date) {
        if (date != null) {
            return formatter.format(date);
        } else {
            return "";
        }
    }
}
