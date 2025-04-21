package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
            VacancyDto receivedDto = vacancyRest.getByN(2L);
            assertEquals(vacancyDto, receivedDto);
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

        VacancyDto receivedDto = vacancyRest.getByN(N);

        assertEquals(2L, receivedDto.getN());
    }

//    @Test
//    void findBy() {
//        VacancyRest vacancyRest = new VacancyRest();
//        vacancyRest.setVacancyService(mockVacancyService);
//        String SEARCH_NAME = "SEARCH_NAME";
//        VacancyDto vacancyDto1 = new VacancyDto();
//        vacancyDto1.setN(1L);
//        VacancyDto vacancyDto2 = new VacancyDto();
//        vacancyDto1.setN(2L);
//        when(mockVacancyService.findByName(SEARCH_NAME)).thenReturn(List.of(vacancyDto1, vacancyDto2));
//
//        VacancyCriterySearch vacancyCriterySearch = new VacancyCriterySearch();
//        vacancyCriterySearch.setByName(SEARCH_NAME);
//
//        List<VacancyDto> dtos = vacancyRest.findBy(vacancyCriterySearch);
//
//        assertEquals(2, dtos.size());
//        assertEquals(vacancyDto1, dtos.get(0));
//        assertEquals(vacancyDto2, dtos.get(1));
//    }

}