package ru.perm.v.vacancy_j.rest;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
//@Transactional
class CompanyRestCacheTest {
//    @Autowired
//    private CompanyService companyService;

    @MockitoBean
    private CompanyService companyService;

    @Test
    public void checkCountCallforGetALL() {
//        CompanyService companyService = mock(CompanyService.class);
        CompanyRest companyRest = new CompanyRest(companyService);
        CompanyDto companyDto1 = new CompanyDto();
        companyDto1.setN(1L);
        CompanyDto companyDto2 = new CompanyDto();
        companyDto2.setN(2L);
        when(companyService.getAll()).thenReturn(List.of(companyDto1, companyDto2));

        companyRest.getAll();
        companyRest.getAll();
        companyRest.getAll();
        ResponseEntity<?> response = companyRest.getAll();
        List<CompanyDto> dtos = (List<CompanyDto>) response.getBody();

        assertEquals(2, dtos.size());
//        assertEquals(-1L, dtos.get(0).getN());
        assertEquals(1L, dtos.get(0).getN());
        assertEquals(2L, dtos.get(1).getN());
//        assertEquals(L, dtos.get(1).getN());

        verify(companyService, times(4)).getAll();
    }
}
