package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class VacancyRestTest {

    VacancyService mockVacancyService = mock(VacancyService.class);

    @Test
    void getByN_notNull() {
        VacancyRest vacancyRest = new VacancyRest();
        Long N = 2L;
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setN(N);
        try {
            when(mockVacancyService.getByN(N)).thenReturn(vacancyDto);
            vacancyRest.setVacancyService(mockVacancyService);
            ResponseEntity ret = vacancyRest.getByN(2L);
            assertEquals(vacancyDto, ret.getBody());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getByN() throws Exception {
        VacancyDto vacancyDto = new VacancyDto();
        Long N = 2L;
        vacancyDto.setN(N);
        VacancyRest vacancyRest = new VacancyRest();
        vacancyRest.setVacancyService(mockVacancyService);
        when(mockVacancyService.getByN(N)).thenReturn(vacancyDto);

        ResponseEntity<VacancyDto> ret = vacancyRest.getByN(N);

        assertEquals(2L, ret.getBody().getN());
    }

    @Test
    void getByNwithException() throws Exception {
        Long N = 2L;
        VacancyRest vacancyRest = new VacancyRest();
        vacancyRest.setVacancyService(mockVacancyService);
        when(mockVacancyService.getByN(N)).thenThrow(new Exception("ERROR"));

        ResponseEntity<?> ret = vacancyRest.getByN(N);

        assertEquals("ERROR", ret.getBody());
        assertEquals(500, ret.getStatusCode().value());
    }

    @Test
    void findBy() {
        VacancyRest vacancyRest = new VacancyRest();
        vacancyRest.setVacancyService(mockVacancyService);
        String SEARCH_NAME = "SEARCH_NAME";
        VacancyDto vacancyDto1 = new VacancyDto();
        vacancyDto1.setN(1L);
        VacancyDto vacancyDto2 = new VacancyDto();
        vacancyDto1.setN(2L);

        VacancyCriterySearch vacancyCriterySearch = new VacancyCriterySearch();
        vacancyCriterySearch.setByName(SEARCH_NAME);

        when(mockVacancyService.findByCritery(vacancyCriterySearch)).thenReturn(List.of(vacancyDto1, vacancyDto2));

        ResponseEntity ret = vacancyRest.findBy(vacancyCriterySearch);
        List<VacancyDto> dtos= (List<VacancyDto>) ret.getBody();

        assertEquals(2, dtos.size());
        assertEquals(vacancyDto1, dtos.get(0));
        assertEquals(vacancyDto2, dtos.get(1));
    }

    @Test
    void update() throws Exception {
        VacancyRest vacancyRest = new VacancyRest();
        Long N = 2L;
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setN(N);
        VacancyDto updatedDto = null;
        try {
            when(mockVacancyService.update(vacancyDto)).thenReturn(vacancyDto);
            vacancyRest.setVacancyService(mockVacancyService);
            ResponseEntity ret = vacancyRest.update(vacancyDto);
            updatedDto = (VacancyDto) ret.getBody();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(vacancyDto, updatedDto);
        verify(mockVacancyService, times(1)).update(vacancyDto);
    }

    @Test
    void updateForNotFound() throws Exception {
        VacancyRest vacancyRest = new VacancyRest();
        Long N = 2L;
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setN(N);
        try {
            when(mockVacancyService.getByN(N)).thenThrow(new Exception("ERROR"));
            vacancyRest.setVacancyService(mockVacancyService);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        ResponseEntity<?> ret = vacancyRest.update(vacancyDto);

        assertEquals(500, ret.getStatusCode().value());
        assertEquals("ERROR", ret.getBody());
        verify(mockVacancyService, never()).update(any());
        verify(mockVacancyService, times(1)).getByN(N);
    }

    @Test
    void getAll() {
        VacancyDto vacancyDto1 = new VacancyDto();
        vacancyDto1.setN(1L);
        VacancyDto vacancyDto2 = new VacancyDto();
        vacancyDto2.setN(2L);
        List<VacancyDto> vacancyDtos = List.of(vacancyDto1, vacancyDto2);
        VacancyRest vacancyRest = new VacancyRest();
        vacancyRest.setVacancyService(mockVacancyService);
        when(mockVacancyService.getAll()).thenReturn(vacancyDtos);
        ResponseEntity<List<VacancyDto>> receivedVacancies = vacancyRest.getAll();

        assertEquals(200, receivedVacancies.getStatusCodeValue());
        assertEquals(2, receivedVacancies.getBody().size());
        assertEquals(vacancyDto1, receivedVacancies.getBody().get(0));
        assertEquals(vacancyDto2, receivedVacancies.getBody().get(1));
    }

    @Test
    public void deleteByN() throws Exception {
        VacancyDto vacancyDto1 = new VacancyDto();
        Long N = 1L;
        vacancyDto1.setN(N);
        VacancyRest vacancyRest = new VacancyRest();
        vacancyRest.setVacancyService(mockVacancyService);
        when(mockVacancyService.getByN(N)).thenThrow(new Exception("ERROR"));
        ResponseEntity<?> response = vacancyRest.deleteByN(N);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("ERROR", response.getBody());
    }
}