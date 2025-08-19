package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.perm.v.vacancy_j.dto.CompanyCriterySearch;
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
    void notFoundOnGetByN() throws Exception {
        CompanyService companyService = mock(CompanyService.class);
        doThrow(new Exception("ERROR")).when(companyService).getByN(1L);
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity<?> responseEntity = companyRest.getByN(1L);

        assertTrue(responseEntity.getStatusCode().is5xxServerError());
        assertEquals("Company with n=1 not found.", responseEntity.getBody());
    }

    @Test
    void create() throws Exception {
        CompanyDto companyDTO = new CompanyDto(10L, "COMPANY");
        CompanyService companyService = mock(CompanyService.class);
        CompanyRest companyRest = new CompanyRest(companyService);
        CompanyDto createdCompanyDTO = new CompanyDto(100L, "CREATED_COMPANY");
        when(companyService.create(companyDTO)).thenReturn(createdCompanyDTO);

        ResponseEntity<?> companyFromRest =  companyRest.create(companyDTO);

        assertEquals(createdCompanyDTO, companyFromRest.getBody());
    }

    @Test
    void exceptionOnCreate() throws Exception {
        Long N = 10L;
        CompanyDto companyDTO = new CompanyDto(N, "FOR UPDATE");

        CompanyService companyService = mock(CompanyService.class);
        doThrow(new Exception("ERROR")).when(companyService).create(companyDTO);
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity<?> responseEntity = companyRest.create(companyDTO);

        assertTrue(responseEntity.getStatusCode().is5xxServerError());
        assertEquals("ERROR", responseEntity.getBody());
    }

    @Test
    void exceptionShortNameOnCreate() throws Exception {
        Long N = 10L;
        CompanyDto companyDTO = new CompanyDto(N, "1234");

        CompanyService companyService = mock(CompanyService.class);
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity<?> responseEntity = companyRest.create(companyDTO);

        assertTrue(responseEntity.getStatusCode().is5xxServerError());
        assertEquals("name: Длина name в CompanyDto должна быть больше 5 символов.", responseEntity.getBody());
    }

    @Test
    void exceptionShortNameOnUpdate() throws Exception {
        Long N = 10L;
        CompanyDto companyDTO = new CompanyDto(N, "1234");

        CompanyService companyService = mock(CompanyService.class);
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity<?> responseEntity = companyRest.update(N, companyDTO);

        assertTrue(responseEntity.getStatusCode().is5xxServerError());
        assertEquals("name: Длина name в CompanyDto должна быть больше 5 символов.", responseEntity.getBody());
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
    void anyErrorInCompanyServiceOnUpdate() throws Exception {
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

    @Test
    public void findByExample() {
        CompanyService companyService = mock(CompanyService.class);
        Long COMPANY_N1 = 1L;
        Long COMPANY_N2 = 2L;
        CompanyDto companyDto1 = new CompanyDto(COMPANY_N1, "NAME");
        CompanyDto companyDto2 = new CompanyDto(COMPANY_N2, "NAME");
        CompanyCriterySearch example = new CompanyCriterySearch();

        when(companyService.findByExample(example)).thenReturn(List.of(companyDto1, companyDto2));
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity<?> ret = companyRest.findByExample(example);

        assertNotNull(ret);
        List<CompanyDto> companies= (List<CompanyDto>) ret.getBody();

        assertEquals(2, companies.size());
        assertEquals(companyDto1, companies.get(0));
        assertEquals(companyDto2, companies.get(1));
    }
}