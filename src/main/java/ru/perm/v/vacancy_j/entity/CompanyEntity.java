package ru.perm.v.vacancy_j.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "company")
public class CompanyEntity {
    @Id
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long n;
    @Column(name = "name", nullable = false)
    private String name;

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
}
