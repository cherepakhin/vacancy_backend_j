package ru.perm.v.vacancy_j.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;

import java.util.Objects;

//@Api(tags = "Описание компании")
//@ApiModel(description = "Описание компании")
public class CompanyDto extends Dto {
    @Size(min = 5, message = "Длина name в CompanyDto должна быть больше 5 символов.")
    @NotNull
//    @ApiModelProperty(notes = "Название компании")
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
        return "CompanyDto{" + "n='" + getN() + ", " +
                "name='" + name + '\'' +
                '}';
    }
}
