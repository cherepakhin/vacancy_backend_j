package ru.perm.v.vacancy_j.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VacancyDtoTest {

    @Test
    void create() {
        VacancyDto vacancyDto = new VacancyDto();
        assertNotNull(vacancyDto);
    }

    @Test
    void testId() {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setN(10L);
        assertEquals(10L, vacancyDto.getN());
    }

    @Test
    void testTitle() {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setTitle("title");
        assertEquals("title", vacancyDto.getTitle());
    }
    @Test
    void testDescription() {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setDescription("description");
        assertEquals("description", vacancyDto.getDescription());
    }

    @Test
    void testCompany() {
        VacancyDto vacancyDto = new VacancyDto();
        CompanyDto companyDto = new CompanyDto();
        companyDto.setN(10L);
        companyDto.setName("company");
        vacancyDto.setCompany(companyDto);

        assertEquals(new CompanyDto(10L, "company"), vacancyDto.getCompany());
    }

    @Test
    void testSource() {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setSource("source");
        assertEquals("source", vacancyDto.getSource());
    }
    @Test
    void testComment() {
        VacancyDto vacancyDto = new VacancyDto();
        Long COMPANY_N = 10L;
        String COMPANY_NAME = "COMPANY";
        vacancyDto.setCompany(new CompanyDto(COMPANY_N, COMPANY_NAME));
        assertEquals(new CompanyDto(COMPANY_N, COMPANY_NAME), vacancyDto.getCompany());
    }

    @Test
    void constructorTest() {
        String TITLE = "TITLE";
        String DESCRIPTION = "DESCRIPTION";
        Long COMPANY_N = 10L;
        String COMPANY_NAME = "COMPANY";
        String SOURCE = "SOURCE";
        String COMMENT = "COMMENT";
        String STATUS = "STATUS";
        String DATE_CHANGED = "2020-01-01";

        VacancyDto vacancyDto = new VacancyDto(TITLE, DESCRIPTION, new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);

        assertEquals(TITLE, vacancyDto.getTitle());
        assertEquals(DESCRIPTION, vacancyDto.getDescription());
        assertEquals(new CompanyDto(COMPANY_N, COMPANY_NAME), vacancyDto.getCompany());
        assertEquals(SOURCE, vacancyDto.getSource());
        assertEquals(COMMENT, vacancyDto.getComment());
        assertEquals(STATUS, vacancyDto.getStatus());
        assertEquals(DATE_CHANGED, vacancyDto.getDateChanged());
    }

    @Test
    void testHashCodeForEqual() {
        Long N = 10L;
        String TITLE = "TITLE";
        String DESCRIPTION = "DESCRIPTION";
        Long COMPANY_N = 10L;
        String COMPANY_NAME = "COMPANY";
        String SOURCE = "SOURCE";
        String COMMENT = "COMMENT";
        String STATUS = "STATUS";
        String DATE_CHANGED = "2020-01-01";

        VacancyDto vacancyDto1 = new VacancyDto(N, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);
        VacancyDto vacancyDto2 = new VacancyDto(N, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);

        assertEquals(vacancyDto1.hashCode(), vacancyDto2.hashCode());
    }

    @Test
    void testHashCodeFor_NOT_Equal() {
        Long N1 = 1L;
        Long N2 = 2L;
        String TITLE = "TITLE";
        String DESCRIPTION = "DESCRIPTION";
        Long COMPANY_N = 10L;
        String COMPANY_NAME = "COMPANY";
        String SOURCE = "SOURCE";
        String COMMENT = "COMMENT";
        String STATUS = "STATUS";
        String DATE_CHANGED = "2020-01-01";

        VacancyDto vacancyDto1 = new VacancyDto(N1, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);
        VacancyDto vacancyDto2 = new VacancyDto(N2, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);

        assertNotEquals(vacancyDto1.hashCode(), vacancyDto2.hashCode());
    }

    @Test
    void testForEqual() {
        Long N = 10L;
        String TITLE = "TITLE";
        String DESCRIPTION = "DESCRIPTION";
        Long COMPANY_N = 10L;
        String COMPANY_NAME = "COMPANY";
        String SOURCE = "SOURCE";
        String COMMENT = "COMMENT";
        String STATUS = "STATUS";
        String DATE_CHANGED = "2020-01-01";

        VacancyDto vacancyDto1 = new VacancyDto(N, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);
        VacancyDto vacancyDto2 = new VacancyDto(N, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);

        assertEquals(vacancyDto1, vacancyDto2);
    }

    @Test
    void testFor_NOT_EqualByN() {
        Long N1 = 1L;
        Long N2 = 2L;
        String TITLE = "TITLE";
        String DESCRIPTION = "DESCRIPTION";
        Long COMPANY_N = 10L;
        String COMPANY_NAME = "COMPANY";
        String SOURCE = "SOURCE";
        String COMMENT = "COMMENT";
        String STATUS = "STATUS";
        String DATE_CHANGED = "2020-01-01";

        VacancyDto vacancyDto1 = new VacancyDto(N1, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);
        VacancyDto vacancyDto2 = new VacancyDto(N2, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);

        assertNotEquals(vacancyDto1, vacancyDto2);
    }
    @Test
    void testFor_NOT_EqualByCompany() {
        Long N = 1L;
        String TITLE = "TITLE";
        String DESCRIPTION = "DESCRIPTION";
        Long COMPANY_N_10 = 10L;
        Long COMPANY_N_20 = 20L;
        String COMPANY_NAME = "COMPANY";
        String SOURCE = "SOURCE";
        String COMMENT = "COMMENT";
        String STATUS = "STATUS";
        String DATE_CHANGED = "2020-01-01";

        VacancyDto vacancyDto1 = new VacancyDto(N, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N_10, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);
        VacancyDto vacancyDto2 = new VacancyDto(N, TITLE, DESCRIPTION,
                new CompanyDto(COMPANY_N_20, COMPANY_NAME), SOURCE, COMMENT, STATUS, DATE_CHANGED);

        assertNotEquals(vacancyDto1, vacancyDto2);
    }
}