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
        vacancyDto.setId(10L);
        assertEquals(10L, vacancyDto.getId());
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
        vacancyDto.setCompany("company");
        assertEquals("company", vacancyDto.getCompany());
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
        vacancyDto.setCompany("company");
        assertEquals("company", vacancyDto.getCompany());
    }
    @Test
    void testCompletedFalse() {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setCompleted(false);
        assertFalse(vacancyDto.getCompleted());
    }

    @Test
    void testCompletedTrue() {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setCompleted(true);
        assertTrue(vacancyDto.getCompleted());
    }

    @Test
    void constructorTest() {
        String TITLE = "TITLE";
        String DESCRIPTION = "DESCRIPTION";
        String COMPANY = "COMPANY";
        String SOURCE = "SOURCE";
        String COMMENT = "COMMENT";
        boolean COMPLETED = true;

        VacancyDto vacancyDto = new VacancyDto(TITLE, DESCRIPTION, COMPANY, SOURCE, COMMENT, COMPLETED);

        assertEquals(TITLE, vacancyDto.getTitle());
        assertEquals(DESCRIPTION, vacancyDto.getDescription());
        assertEquals(COMPANY, vacancyDto.getCompany());
        assertEquals(SOURCE, vacancyDto.getSource());
        assertEquals(COMMENT, vacancyDto.getComment());
        assertEquals(COMPLETED, vacancyDto.getCompleted());
    }
}