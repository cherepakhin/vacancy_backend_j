package ru.perm.v.vacancy_j.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class VacancyEntity {
    @Id
    @Column(name = "n", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long n = -1L;

    //TODO: add date created, date modified

    // short description
    @Column(name = "title", nullable = false)
    private String title = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_n", nullable = false)
    private CompanyEntity companyEntity = new CompanyEntity(-1L,"");

    // full description
    @Column(name = "description", nullable = false)
    private String description = "";

    @Column(name = "link", nullable = false)
    private String link = "";

    @Column(name = "comment", nullable = false)
    private String comment = "";

    @Column(name = "status", nullable = false)
    private String status = "";

    public VacancyEntity() {
        super();
    }

    public VacancyEntity(Long n, String title, CompanyEntity companyEntity, String description,
                         String link, String comment, String status) {
        this();
        this.n = n;
        this.title = title;
        this.companyEntity = companyEntity;
        this.description = description;
        this.link = link;
        this.comment = comment;
        this.status = status;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VacancyEntity that)) return false;
        return Objects.equals(n, that.n) && Objects.equals(title, that.title) && Objects.equals(companyEntity, that.companyEntity) && Objects.equals(description, that.description) && Objects.equals(link, that.link) && Objects.equals(comment, that.comment) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(n, title, companyEntity, description, link, comment, status);
    }

    @Override
    public String toString() {
        return "VacancyEntity{" +
                "n=" + n +
                ", title='" + title + '\'' +
                ", companyEntity=" + companyEntity +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
