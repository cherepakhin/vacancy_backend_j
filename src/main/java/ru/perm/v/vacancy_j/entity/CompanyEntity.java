package ru.perm.v.vacancy_j.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "company")
public class CompanyEntity {
    @Id
    @Column(name = "n", nullable = false)
    private Long n = -1L;
    @Column(name = "name", nullable = false)
    private String name = "";

    public CompanyEntity() {
        super();
    }

    public CompanyEntity(Long n, String name) {
        super();
        this.n = n;
        this.name = name;
    }

    public Long getN() {
        return n;
    }

    public void setN(Long n) {
        this.n = n;
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
        if (!(o instanceof CompanyEntity that)) return false;
        return Objects.equals(n, that.n) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(n, name);
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "n=" + n +
                ", name='" + name + '\'' +
                '}';
    }
}
