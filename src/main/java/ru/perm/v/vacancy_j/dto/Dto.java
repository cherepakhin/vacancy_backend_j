package ru.perm.v.vacancy_j.dto;

import java.util.Objects;

public abstract class Dto {
    private Long id = -1L;

    protected Dto() {
        this.id = -1L;
    }

    protected Dto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dto dto)) return false;
        return Objects.equals(id, dto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
