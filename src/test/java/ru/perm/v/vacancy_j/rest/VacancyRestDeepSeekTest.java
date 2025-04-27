package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Generated DeepSeek
 * https://chat.deepseek.com/a/chat/s/a565e792-0e0c-4d6d-b747-54e741c7e3ac
 */
class VacancyRestDeepSeekTest {

    @Mock
    private VacancyService vacancyService;

    @Mock
    private Logger log;

    @InjectMocks
    private VacancyRest vacancyRest;

    CompanyDto COMPANY_DTO;
    VacancyDto testVacancyDto1;
    private VacancyCriterySearch testSearchCriteria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testVacancyDto1 = new VacancyDto();
        Long N = 1L;
        String TITLE = "TITLE";
        String DESCRIPTION = "DESCRIPTION";
        String LINK = "LINK";
        String COMMENT = "COMMENT";
        Long COMPANY_N = 10L;
        String COMPANY_NAME = "COMPANY_NAME";
        COMPANY_DTO = new CompanyDto(COMPANY_N, COMPANY_NAME);
        testVacancyDto1.setCompany(COMPANY_DTO);
        testVacancyDto1.setN(N);
        testVacancyDto1.setTitle(TITLE);
        testVacancyDto1.setDescription(DESCRIPTION);
        testVacancyDto1.setSource(LINK);
        testVacancyDto1.setComment(COMMENT);
        testSearchCriteria = new VacancyCriterySearch();
        testSearchCriteria.setByName("Test");
    }

    @Test
    void findBy_ShouldReturnMatchingVacancies() {
        // Arrange
        List<VacancyDto> expected = Arrays.asList(testVacancyDto1);
        when(vacancyService.findByCritery(testSearchCriteria)).thenReturn(expected);

        // Act
        ResponseEntity<?> response = vacancyRest.findBy(testSearchCriteria);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(log).info("find by " + testSearchCriteria);
    }

    @Test
    void deleteByN_ShouldDeleteExistingVacancy() throws Exception {
        // Arrange
        when(vacancyService.getByN(1L)).thenReturn(testVacancyDto1);
        doNothing().when(vacancyService).deleteByN(1L);

        // Act
        ResponseEntity<?> response = vacancyRest.deleteByN(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(vacancyService).deleteByN(1L);
        verify(log).info("DELETE vacancy n=1");
    }

    @Test
    void deleteByN_ShouldReturnErrorWhenVacancyNotFound() throws Exception {
        // Arrange
        String errorMessage = "Vacancy not found";
        when(vacancyService.getByN(1L)).thenThrow(new NoSuchElementException(errorMessage));

        // Act
        ResponseEntity<?> response = vacancyRest.deleteByN(1L);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
        verify(vacancyService, never()).deleteByN(1L);
    }

    @Test
    void deleteByN_ShouldReturnErrorWhenDeletionFails() throws Exception {
        // Arrange
        String errorMessage = "Deletion failed";
        when(vacancyService.getByN(1L)).thenReturn(testVacancyDto1);
        doThrow(new RuntimeException(errorMessage)).when(vacancyService).deleteByN(1L);

        // Act
        ResponseEntity<?> response = vacancyRest.deleteByN(1L);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    void testVacancyServiceGetterAndSetter() {
        // Arrange
        VacancyService newService = mock(VacancyService.class);

        // Act
        vacancyRest.setVacancyService(newService);
        VacancyService result = vacancyRest.getVacancyService();

        // Assert
        assertEquals(newService, result);
    }
}