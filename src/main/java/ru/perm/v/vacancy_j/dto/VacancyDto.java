package ru.perm.v.vacancy_j.dto;

import java.util.Objects;

public class VacancyDto extends Dto {
    private Long n = -1L;
    private String title = "";
    private String description = "";
    private CompanyDto company = new CompanyDto();
    private String source = "";
    private String comment = "";
    private Boolean completed = false;

    public VacancyDto() {
        super();
    }

    public VacancyDto(String title, String description, CompanyDto companyDto, String source, String comment, Boolean completed) {
        super();
        this.title = title;
        this.description = description;
        this.company = companyDto;
        this.source = source;
        this.comment = comment;
        this.completed = completed;
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
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VacancyDto that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(n, that.n) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(company, that.company) && Objects.equals(source, that.source) && Objects.equals(comment, that.comment) && Objects.equals(completed, that.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), n, title, description, company, source, comment, completed);
    }

    @Override
    public String toString() {
        return "VacancyDto{" +
                "n=" + n +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", company=" + company +
                ", source='" + source + '\'' +
                ", comment='" + comment + '\'' +
                ", completed=" + completed +
                '}';
    }
}
