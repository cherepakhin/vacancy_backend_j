package ru.perm.v.vacancy_j.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Schema(description = "Описание компании")
public class CompanyDto extends Dto {
    @Size(min = 5, message = "Длина name в CompanyDto должна быть больше 5 символов.")
    @NotNull
    @Schema(description = "Название компании")
    private String name;

    public CompanyDto() {
        this.name = "";
    }

    public CompanyDto(Long n, String name) {
        super(n);
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

    @Override
    public String toString() {
        return "CompanyDto{" + "n=" + getN() + ", " +
                "name='" + name + '\'' +
                '}';
    }
}
