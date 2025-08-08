package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;

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

        assertEquals("Company update n=10 CompanyDto{n=10, name='FOR UPDATE'}", message);
    }

    @Test
    void errorOnCompanyServiceUpdate() {
        Long N = 10L;
        CompanyDto forUpdateDTO = new CompanyDto(N, "FOR UPDATE");
        CompanyService companyService = mock(CompanyService.class);
        CompanyRest companyRest = new CompanyRest(companyService);
        try {
            when(companyService.update(forUpdateDTO)).thenThrow(new Exception());
        } catch (Exception e) {
            fail();
        }
        Exception excpt = null; // fake test exception
        try {
            doThrow(new Exception("ERROR MESSAGE")).when(companyService).update(forUpdateDTO);
        } catch (Exception e) {
            excpt = e;
        }

        ResponseEntity<?> response = companyRest.update(N, forUpdateDTO);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("ERROR MESSAGE", response.getBody());

        try {
            verify(companyService, times(1)).update(forUpdateDTO);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAll() {
        CompanyService companyService = mock(CompanyService.class);
        Long COMPANY_N1 = 1L;
        Long COMPANY_N2 = 2L;
        CompanyDto companyDto1 = new CompanyDto(COMPANY_N1, "NAME");
        CompanyDto companyDto2 = new CompanyDto(COMPANY_N2, "NAME");
        when(companyService.getAll()).thenReturn(List.of(companyDto1, companyDto2));
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity<?> ret = companyRest.getAll();

        assertNotNull(ret);
        List<CompanyDto> companies= (List<CompanyDto>) ret.getBody();

        assertEquals(2, companies.size());
        assertEquals(companyDto1, companies.get(0));
        assertEquals(companyDto2, companies.get(1));
    }
}