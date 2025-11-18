package ru.perm.v.vacancy_j.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// https://www.perplexity.ai/search/napishi-unit-test-dlia-https-g-vGJ2l23USiyLuXn79QLCKA
// напиши unit test для https://github.com/cherepakhin/vacancy_backend_j/blob/dev/src/main/java/ru/perm
// Не удалось получить содержимое файла EchoRest.java напрямую по ссылке. Пожалуйста, предоставьте содержимое файла EchoRest.java, чтобы можно было написать для него unit тест.
// засунул код CompanyRest.java в запрос.

// С десяток мелких исправлений, после генерации
//1. Напридумывал классы, ктр. нет в проекте. Пример: CompanyDTO, в проекте CompanyDto
//
//2. Напридумывал конструкторы, ктр. нет.
//        Пример:
//Есть конструктор CompanyDto(Long n, String name).
//Придумал конструктор
//CompanyDto(1L, "Company A", "Description A");
//Поля description нет вообще. Придумано.
//
//3. Придумал методы.
//        companyService.getById() - нет такого метода. Есть метод companyService.getByN()
//
//4. У придуманного метода придумал тип возвращаемого значения.
//
//5. Придумал URL "/api/company/"
//В проекте "/api/company/"
//
//        6. С кешем его работать не учили.
//
//        7. Нет тестов на ошибки.
// Короче, весь тест пришлось править.

@WebMvcTest(CompanyRest.class)
class CompanyRestPerplexityMvcTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CompanyRest companyRest;

    @MockitoBean
    private CompanyService companyService;

    ObjectMapper mapper =  new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(companyRest).build();
    }

    @Test
    void getAll_ShouldReturnCompaniesList() throws Exception {
        // Given
        CompanyDto company1 = new CompanyDto(1L, "Company A");
        CompanyDto company2 = new CompanyDto(2L, "Company B");
        when(companyService.getAll()).thenReturn(List.of(company1, company2));

        // When & Then
        mockMvc.perform(get("/company/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].n").value(1L))
                .andExpect(jsonPath("$[0].name").value("Company A"))
                .andExpect(jsonPath("$[1].n").value(2L))
                .andExpect(jsonPath("$[1].name").value("Company B"));

        verify(companyService, times(1)).getAll();
    }

    @Test
    void getByN_WhenCompanyExists_ShouldReturnCompany() throws Exception {
        // Given
        Long N = 1L;
        CompanyDto company = new CompanyDto(N, "Company A");
        when(companyService.getByN(N)).thenReturn(company);

        // When & Then
        mockMvc.perform(get("/company/{n}", N))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.n").value(N))
                .andExpect(jsonPath("$.name").value("Company A"));

        verify(companyService, times(1)).getByN(N);
    }

    @Test
    void getByN_WhenCompanyDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Given
        Long id = 999L;
        doThrow(new Exception("Company not found")).when(companyService).getByN(eq(id));

        // When & Then
        mockMvc.perform(get("/company/{id}", id))
                .andExpect(status().is5xxServerError());

        verify(companyService, times(1)).getByN(id);
    }

    @Test
    void create_ShouldReturnCreatedCompanyCheckWitExpect() throws Exception {
        // Given
        CompanyDto savedCompany = new CompanyDto(1L, "New Company");

        when(companyService.create(new CompanyDto(-1L, "New Company"))).thenReturn(savedCompany);
        String jsonRequest = """
                {
                    "name": "New Company"
                }
                """;

        // When & Then
        mockMvc.perform(put("/company/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.n").value(1L))
                .andExpect(jsonPath("$.name").value("New Company"));

        verify(companyService, times(1)).create(any(CompanyDto.class));
    }

    @Test
    void create_ShouldReturnCreatedCompanyWithCheckBody() throws Exception {
        CompanyDto companyForSave = new CompanyDto(1L, "New Company");
        String requestJson = mapper.writeValueAsString(companyForSave);
        when(companyService.create(new CompanyDto(-1L, "New Company"))).thenReturn(companyForSave);

        MvcResult result = mockMvc.perform(put("/company/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(status().isCreated())
                .andReturn();
        CompanyDto savedCompany = mapper.readValue(result.getResponse().getContentAsString(), CompanyDto.class);

        assertEquals(savedCompany, companyForSave);
        verify(companyService, times(1)).create(any(CompanyDto.class));
    }

    @Test
    void update_WhenCompanyExists() throws Exception {
        // Given
        Long ID = 100L;
        CompanyDto companyForUpdate = new CompanyDto(ID, "Company for update1");
        CompanyDto updatedCompany = new CompanyDto(ID, "Company updated");

        when(companyService.update(companyForUpdate)).thenReturn(updatedCompany);

        String jsonRequest = """
                {
                    "n": 100,
                    "name": "Company for update1"
                }
                """;

        // When
        MvcResult mvcResult = mockMvc.perform(post("/company/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
// DEBUG
//        System.out.println("------------------------------------result");
//        System.out.println(result);
//        System.out.println("------------------------------------getResponse");
//        System.out.println(result.getResponse());
//        System.out.println("------------------------------------getContentAsString");
//        System.out.println(result.getResponse().getContentAsString());
        CompanyDto companyDto = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                CompanyDto.class);
        assertEquals(updatedCompany, companyDto);
        verify(companyService, times(1)).update(any(CompanyDto.class));
    }

    @Test
    void delete_WhenCompanyExists() throws Exception {
        // Given
        Long ID = 1L;
        doNothing().when(companyService).delete(ID);
        // When & Then
        mockMvc.perform(delete("/company/{id}", ID))
                .andExpect(status().isOk());

        verify(companyService, times(1)).delete(ID);
    }
}