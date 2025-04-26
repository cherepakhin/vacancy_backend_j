package ru.perm.v.vacancy_j.dto;

import java.util.Objects;

public class VacancyDto extends Dto {
    private Long n = -1L;
    private String title = "";
    private String description = "";
    private CompanyDto company = new CompanyDto();
    private String link = "";
    private String comment = "";

    public VacancyDto() {
        super();
    }

    public VacancyDto(Long n, String title, String description, CompanyDto companyDto, String link, String comment) {
        this(title, description, companyDto, link, comment);
        this.n = n;
    }

    public VacancyDto(String title, String description, CompanyDto companyDto, String link, String comment) {
        super();
        this.title = title;
        this.description = description;
        this.company = companyDto;
        this.link = link;
        this.comment = comment;
    }

    @Override
    public Long getN() {
        return n;
    }

    @Override
    public void setN(Long n) {
        this.n = n;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public String getSource() {
        return link;
    }

    public void setSource(String link) {
        this.link = link;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VacancyDto that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(n, that.n) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(company, that.company) && Objects.equals(link, that.link) && Objects.equals(comment, that.comment) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), n, title, description, company, link, comment);
    }

    @Override
    public String toString() {
        return "VacancyDto{" +
                "n=" + n +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", company=" + company +
                ", link='" + link + '\'' +
                ", comment='" + comment +
                '}';
    }
}
