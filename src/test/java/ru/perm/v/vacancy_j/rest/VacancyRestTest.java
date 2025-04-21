package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VacancyRestTest {

    VacancyService mockVacancyService = mock(VacancyService.class);

    @Test
    void getByN_notNull() {
        VacancyRest vacancyRest = new VacancyRest();
        assertNotNull(vacancyRest.getByN(100L));
    }

    @Test
    void getByN() {
        VacancyRest vacancyRest = new VacancyRest();
        assertEquals(100L, vacancyRest.getByN(100L).getN());
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