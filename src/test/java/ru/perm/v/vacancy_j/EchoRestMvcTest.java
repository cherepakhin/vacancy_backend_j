package ru.perm.v.vacancy_j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.perm.v.vacancy_j.rest.EchoRest;

import static org.junit.jupiter.api.Assertions.fail;

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
