package ru.perm.v.vacancy_j.dto;

import java.util.Objects;

public class VacancyDto extends Dto {
    private String title = "";
    private String description = "";
    private String company = "";
    private String source = "";
    private String comment = "";
    private Boolean completed = false;

    public VacancyDto() {
        super();
    }

    public VacancyDto(String title, String description, String company,
                      String source, String comment, Boolean completed) {
        this();
        this.title = title;
        this.description = description;
        this.company = company;
        this.source = source;
        this.comment = comment;
        this.completed = completed;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
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
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(company, that.company) && Objects.equals(source, that.source) && Objects.equals(comment, that.comment) && Objects.equals(completed, that.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, description, company, source, comment, completed);
    }
}
