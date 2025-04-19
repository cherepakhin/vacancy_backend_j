package ru.perm.v.vacancy_j.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class VacancyEntity {
    @Id
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long n = -1L;

    // short description
    @Column(name = "title", nullable = false)
    private String title = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_n", nullable = false)
    private CompanyEntity companyEntity = new CompanyEntity(-1L,"");

    // full description
    @Column(name = "description", nullable = false)
    private String description = "";

    public VacancyEntity() {
        super();
    }

    public VacancyEntity(Long n, String title, CompanyEntity companyEntity, String description) {
        this();
        this.n = n;
        this.title = title;
        this.companyEntity = companyEntity;
        this.description = description;
    }

    public Long getN() {
        return n;
    }

    public void setN(Long n) {
        this.n = n;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VacancyEntity that)) return false;
        return Objects.equals(n, that.n) && Objects.equals(title, that.title) && Objects.equals(companyEntity, that.companyEntity) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(n, title, companyEntity, description);
    }
}
