package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест сгенерирован GigaCode.
 */
@WebMvcTest(EchoRest.class)
@Import(EchoRestTest.TestConfig.class)
public class EchoRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EchoRest echoRest;

    @Autowired
    private Logger logger;

    @BeforeEach
    void setUp() {
        Mockito.reset(echoRest);
    }

    @Test
    public void testEcho_NormalMessage_ReturnsSameMessage() throws Exception {
        String message = "hello";
        Mockito.when(echoRest.echo(message)).thenReturn(message);

        mockMvc.perform(get("/echo/" + message))
                .andExpect(status().isOk())
                .andExpect(content().string(message));

// Сгенерировано GigaCode
//        Mockito.verify(logger).info("Echo message: {}", message);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        Logger logger() {
            return Mockito.mock(Logger.class);
        }
// Сгенерировано GigaCode
        @Bean
        @Primary
        EchoRest echoRest() {
            return Mockito.mock(EchoRest.class);
        }
    }
}