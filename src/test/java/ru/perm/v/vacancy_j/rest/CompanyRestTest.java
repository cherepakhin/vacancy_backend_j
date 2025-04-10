package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
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

        CompanyDto receivedDTO = companyRest.getByN(1L);

        assertNotNull(receivedDTO);
        assertEquals(companyDto, receivedDTO);
    }

    @Test
    void getByN() throws Exception {
        CompanyService companyService = mock(CompanyService.class);
        Long COMPANY_N = 1L;
        CompanyDto companyDto = new CompanyDto(COMPANY_N, "NAME");
        when(companyService.getByN(COMPANY_N)).thenReturn(companyDto);
        CompanyRest companyRest = new CompanyRest(companyService);

        CompanyDto receivedDTO = companyRest.getByN(1L);

        assertNotNull(receivedDTO);
        assertEquals(companyDto, receivedDTO);
        assertEquals(1L, companyRest.getByN(1L).getN());
    }
}