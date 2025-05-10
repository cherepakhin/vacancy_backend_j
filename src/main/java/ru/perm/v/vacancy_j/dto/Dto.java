package ru.perm.v.vacancy_j.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Abstract DTO")
public abstract class Dto {
    @Schema(description = "ID DTO")
    private Long n = -1L;

    protected Dto() {
        this.n = -1L;
    }

    protected Dto(Long n) {
        this.n = n;
    }

    public Long getN() {
        return n;
    }

    public void setN(Long n) {
        this.n = n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dto dto)) return false;
        return Objects.equals(n, dto.n);
    }

    @Override
    public int hashCode() {
        return Objects.hash(n);
    }
}
