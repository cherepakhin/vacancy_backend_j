package ru.perm.v.vacancy_j.rest;

import java.util.Objects;

public class CriterySearch {
    String byName = "";

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CriterySearch that)) return false;
        return Objects.equals(byName, that.byName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byName);
    }

    @Override
    public String toString() {
        return "CriterySearch{" +
                "byName='" + byName + '\'' +
                '}';
    }
}
