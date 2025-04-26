package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.service.CompanyService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyRestTest {

    @Test
    void getByN_notNull() throws Exception {
        CompanyService companyService = mock(CompanyService.class);
        Long COMPANY_N = 1L;
        CompanyDto companyDto = new CompanyDto(COMPANY_N, "NAME");
        when(companyService.getByN(COMPANY_N)).thenReturn(companyDto);
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity<?> ret = companyRest.getByN(1L);

        assertNotNull(ret.getBody());
        assertEquals(companyDto, ret.getBody());
    }

    @Test
    void getByN() throws Exception {
        CompanyService companyService = mock(CompanyService.class);
        Long COMPANY_N = 1L;
        CompanyDto companyDto = new CompanyDto(COMPANY_N, "NAME");
        when(companyService.getByN(COMPANY_N)).thenReturn(companyDto);
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity<?> ret = companyRest.getByN(1L);

        assertNotNull(ret);
        assertEquals(companyDto, ret.getBody());
        CompanyDto recivedDto = (CompanyDto) ret.getBody();
        assertEquals(companyDto, recivedDto);
    }

    @Test
    void updateForExist() throws Exception {
        Long N = 10L;
        CompanyDto forUpdateDTO = new CompanyDto(N, "FOR UPDATE");
        CompanyService companyService = mock(CompanyService.class);
        CompanyRest companyRest = new CompanyRest(companyService);
        try {
            when(companyService.update(forUpdateDTO)).thenReturn(forUpdateDTO);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        ResponseEntity<?> response = companyRest.update(N, forUpdateDTO);

        CompanyDto updatedDTO = (CompanyDto) response.getBody();
        assertEquals(forUpdateDTO, updatedDTO);
        verify(companyService, times(1)).update(forUpdateDTO);
    }

    @Test
    void errorOnUpdate() throws Exception {
        Long N = 10L;
        CompanyDto forUpdateDTO = new CompanyDto(N, "FOR UPDATE");
        CompanyService companyService = mock(CompanyService.class);
        CompanyRest companyRest = new CompanyRest(companyService);
        doThrow(new Exception("ERROR")).when(companyService).update(forUpdateDTO);

        ResponseEntity<?> ret = companyRest.update(N, forUpdateDTO);
        assertEquals("ERROR", ret.getBody());
    }

    @Test
    void updateForNotExistN() throws Exception {
        Long N = 10L;
        CompanyDto forUpdateDTO = new CompanyDto(N, "FOR UPDATE");
        CompanyService companyService = mock(CompanyService.class);
        CompanyRest companyRest = new CompanyRest(companyService);
        doThrow(new Exception("ERROR")).when(companyService).getByN(N);

        ResponseEntity<?> ret = companyRest.update(N, forUpdateDTO);
        assertEquals("ERROR", ret.getBody());
    }

    @Test
    void formatMessage() {
        Long N = 10L;
        CompanyDto companyDto = new CompanyDto(N, "FOR UPDATE");
        String message = String.format("Company update n=%s %s", N, companyDto);

        assertEquals("Company update n=10 CompanyDto{n='10, name='FOR UPDATE'}", message);
    }
}