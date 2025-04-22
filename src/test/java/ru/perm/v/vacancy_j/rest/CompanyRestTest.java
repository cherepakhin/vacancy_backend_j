package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyRestTest {

    @Test
    void getByN_notNull() throws Exception {
        CompanyService companyService = mock(CompanyService.class);
        Long COMPANY_N = 1L;
        CompanyDto companyDto = new CompanyDto(COMPANY_N, "NAME");
        when(companyService.getByN(COMPANY_N)).thenReturn(companyDto);
        CompanyRest companyRest = new CompanyRest(companyService);

        ResponseEntity ret = companyRest.getByN(1L);

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
}