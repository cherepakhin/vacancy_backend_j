package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EchoRest.class)
@Import(EchoRestGigaTest.TestConfig.class)
public class EchoRestGigaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Logger logger;

    // Пояснения по тесту:
    // EchoRest создается полностью, штатно SpringBoot-ом и Logger объявлен через @Autowrired
    // НО. Бин Loggerа создается в конфигурации EchoRestGigaTest.TestConfig
    // Причем создается хитро, в runtime, через штатный метод с @Bean, @Primary (и это внедряется в контекст)
    // НО!!! Этот бин создается как мок и его работу можно протестировать.
    // УХХХХ!!!
    @Test
    public void testEcho_NormalMessage_ReturnsSameMessage() throws Exception {
        String message = "hello";

        mockMvc.perform(get("/echo/" + message))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
        assertNotNull(logger);
        logger.info("Echo message: {}", message);
        org.mockito.Mockito.verify(logger).info("Echo message: {}", message);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        Logger logger() {
            return org.mockito.Mockito.mock(Logger.class);
        }
    }
}