package ru.perm.v.vacancy_j.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VacancyCriterySearch {
    List<Long> nn = new ArrayList<>();

    /**
     * for like use string with '%'. Example: criterySearch.setByName("%1");
     */
    String byName = "";

    public VacancyCriterySearch() {
        super();
    }

    public VacancyCriterySearch(List<Long> nn, String byName) {
        this();
        this.nn = nn;
        this.byName = byName;
    }

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
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
        return Objects.equals(byName, that.byName) && Objects.equals(nn, that.nn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byName, nn);
    }

    @Override
    public String toString() {
        return "VacancyCriterySearch{" +
                "byName='" + byName + '\'' +
                ", nn=" + nn +
                '}';
    }
}
