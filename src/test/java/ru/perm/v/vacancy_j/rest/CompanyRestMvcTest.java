package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.perm.v.vacancy_j.VacancyApplication;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VacancyApplication.class)
@TestPropertySource(
        locations = "classpath:application-dev.yml")
public class CompanyRestMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompanyService companyService;

    @Test
    void getAll() throws Exception {
        when(companyService.getByN(1L)).thenReturn(new CompanyDto(1L, "Company1"));
        mockMvc.perform(get("/company/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.n").value("1"))
                .andExpect(jsonPath("$.name").value("Company1"));
    }
}
