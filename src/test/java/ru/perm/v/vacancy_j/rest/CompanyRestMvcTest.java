package ru.perm.v.vacancy_j.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.perm.v.vacancy_j.VacancyApplication;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VacancyApplication.class)
@TestPropertySource(
        locations = "classpath:application-dev.yml")
class CompanyRestMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompanyService companyService;

    @Test
    void getByN() throws Exception {
        when(companyService.getByN(1L)).thenReturn(new CompanyDto(1L, "Company1"));
        mockMvc.perform(get("/company/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.n").value("1"))
                .andExpect(jsonPath("$.name").value("Company1"));
    }
    @Test
    void getAll() throws Exception {
        when(companyService.getAll()).thenReturn(List.of(
                new CompanyDto(1L, "Company1"),
                new CompanyDto(2L, "Company2")
                ));
        mockMvc.perform(get("/company/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].n").value("1"))
                .andExpect(jsonPath("$[0].name").value("Company1"))
                .andExpect(jsonPath("$[1].n").value(2L))
                .andExpect(jsonPath("$[1].name").value("Company2"));
    }

    @Test
    void getAllResult() throws Exception {
        when(companyService.getAll()).thenReturn(List.of(
                new CompanyDto(1L, "Company1"),
                new CompanyDto(2L, "Company2")
        ));
        MvcResult mvcResult=mockMvc.perform(get("/company/"))
                .andExpect(status().isOk()).andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());

        String response = mvcResult.getResponse().getContentAsString();
        List<CompanyDto> resultMap = new ObjectMapper().readValue(response, new TypeReference<>() {});

        assertEquals(2, resultMap.size());
        assertEquals(new CompanyDto(1L, "Company1"), resultMap.get(0));
        assertEquals(new CompanyDto(2L, "Company2"), resultMap.get(1));
    }
}
