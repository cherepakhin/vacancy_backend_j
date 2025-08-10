package ru.perm.v.vacancy_j.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VacancyCriterySearch {
    List<Long> nn = new ArrayList<>();

    /**
     * for like use string with '%'. Example: criterySearch.setByName("%1");
     */
    String byTitle = "";

    public VacancyCriterySearch() {
        super();
    }

    public VacancyCriterySearch(List<Long> nn, String byTitle) {
        this();
        this.nn = nn;
        this.byTitle = byTitle;
    }

    public String getByTitle() {
        return byTitle;
    }

    public void setByTitle(String byTitle) {
        this.byTitle = byTitle;
    }

    public List<Long> getNn() {
        return nn;
    }

    public void setNn(List<Long> nn) {
        this.nn = nn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VacancyCriterySearch that)) return false;
        return Objects.equals(nn, that.nn) && Objects.equals(byTitle, that.byTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nn, byTitle);
    }

    @Override
    public String toString() {
        return "VacancyCriterySearch{" +
                "byTitle='" + byTitle + '\'' +
                ", nn=" + nn +
                '}';
    }
}
