package ru.perm.v.vacancy_j.dto;


import java.util.Objects;

public class JobSiteDto {
    private String name = "";
    private String url = "";

    public JobSiteDto() {
    }

    /**
     * Constructor for line. Line format: name;url
     * @param line - example "hh.ru;https://hh.ru"
     */
    public JobSiteDto(String line) {
        String[] fields=line.trim().split(";");
        this.name = fields[0];
        this.url = fields[1];
    }

    public JobSiteDto(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobSiteDto extSite)) return false;
        return Objects.equals(name, extSite.name) && Objects.equals(url, extSite.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return "ExtSiteDto{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
