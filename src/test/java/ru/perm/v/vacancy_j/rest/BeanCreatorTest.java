package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тестирование создания бинов в Spring Boot
*/
@WebMvcTest(EchoRest.class) // Нужно чтобы поднять контекст Spring Boot. Вместо EchoRest можно указать любой сервис
@Import(BeanCreatorTest.TestConfig.class)
public class BeanCreatorTest {

//    @Autowired
//    private MockMvc mockMvc;

    // Этот типа обычный bean из Spring Context, т.к. @Autowired,
    // Но реально он создается в @TestConfiguration.
    @Autowired
    Logger logger;

    // В реальном проекте подобные beans создаются в @Configuration
    @TestConfiguration
    static class TestConfig {
        // Генерация бина для тестирования
        @Bean
        @Primary
        Logger logger() {
            // В контекст вставляется не реальный бин, а мок-объект.
            // Примечание: для тестов можно обойтись @MockBean,
            // но тут тестируется схема работы ГЕНЕРАЦИИ бинов в Spring Boot
            System.out.println("Create mock Logger");
            return Mockito.mock(Logger.class);
        }
    }

    // Пояснения по тесту:
    // EchoRest создается полностью, штатно SpringBoot-ом и Logger объявлен через @Autowrired
    // НО. Бин Loggerа создается в конфигурации EchoRestGigaTest.TestConfig
    // Причем создается хитро, в runtime, через штатный метод с @Bean, @Primary (и это внедряется в контекст)
    // НО!!! Этот бин создается как мок и его работу можно протестировать.
    // https://reflectoring.io/spring-boot-testconfiguration/
    @Test
    public void testLogger() throws Exception {
        String message = "hello";

        // MOCK logger создался в Spring контексте?
        // Он помечен как @Autowired, но создается в конфигурации TestConfig
        assertNotNull(logger);

        // test
        logger.info("Echo message: {}", message);

        // verify
        org.mockito.Mockito.verify(logger).info("Echo message: {}", message);
    }
}