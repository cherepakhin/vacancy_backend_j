package ru.perm.v.vacancy_j.dto;

import java.util.Objects;

public class CompanyDto extends Dto {
    private String name;

    public CompanyDto() {
        this.name = "";
    }

    public CompanyDto(Long id, String name) {
        super(id);

        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDto that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
