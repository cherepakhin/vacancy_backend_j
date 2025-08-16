package ru.perm.v.vacancy_j.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Schema(description = "Описание вакансии")
public class VacancyDto extends Dto {
    @Schema(description = "ID вакансии")
    private Long n = -1L;
    @Size(min = 5, message = "Длина должна быть больше 5 символов.")
    @NotEmpty
    @Schema(description = "Короткое имя")
    private String title = "";
    @Schema(description = "Описание")
    private String description = "";
    @Schema(description = "Компания")
    private CompanyDto company = new CompanyDto();
    @Schema(description = "Источник, ссылка")
    private String source = "";
    @Schema(description = "Комментарий")
    private String comment = "";
    @Schema(description = "Состояние (in_plan, in_work, deleted)")
    private String status = "";
    @Schema(description = "Дата изменения")
    private String dateChanged = "";

    public VacancyDto() {
        super();
    }

    public VacancyDto(Long n, String title, String description, CompanyDto companyDto,
                      String source, String comment, String status, String dateChanged) {
        this(title, description, companyDto, source, comment, status, dateChanged);
        this.n = n;
    }

    public VacancyDto(String title, String description, CompanyDto companyDto, String source,
                      String comment, String status, String dateChanged) {
        super();
        this.title = title;
        this.description = description;
        this.company = companyDto;
        this.source = source;
        this.comment = comment;
        this.status = status;
        this.dateChanged = dateChanged;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VacancyDto that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(n, that.n) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(company, that.company) && Objects.equals(source, that.source) && Objects.equals(comment, that.comment) && Objects.equals(status, that.status) && Objects.equals(dateChanged, that.dateChanged);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), n, title, description, company, source, comment, status, dateChanged);
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
                ", status='" + status + '\'' +
                ", dateChanged='" + dateChanged + '\'' +
                '}';
    }
}
