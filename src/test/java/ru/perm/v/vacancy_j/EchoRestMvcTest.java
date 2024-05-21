package ru.perm.v.vacancy_j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.perm.v.vacancy_j.rest.EchoRest;
import ru.perm.v.vacancy_j.rest.VacancyRest;

@WebMvcTest(EchoRest.class)
public class EchoRestMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEchoStatus() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/echo/aaa"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    @Test
    void getEchoMessage() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/echo/aaa"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("aaa"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
