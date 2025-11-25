package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Тестирование создания бина в Spring Boot
 */
@WebMvcTest(EchoRest.class) // Нужно чтобы поднять контекст Spring Boot. Вместо EchoRest можно указать любой сервис
@Import(BeanCreatorTest.TestConfig.class) // генерируемый контекст. См ниже
public class BeanCreatorTest {

    // Этот типа обычный bean из Spring Context (типа RestController и т.п.)
    // Он типа @Autowired, НО (!!!) реально он создается в TestConfig.
    // См. выше @Import(BeanCreatorTest.TestConfig.class)
    @Autowired
    Logger logger;

    // В реальном проекте подобные beans создаются автоматом или в @Configuration
    // Можно вынести в отдельный файл
    // static потому что должен создаваться раньше всех и в единственном экземпляре
    @TestConfiguration
    static class TestConfig {
        // Генерация бина "logger" для тестирования
        @Bean
        Logger logger() {
            // ДЛЯ ТЕСТА в контекст вставляется не реальный бин, а мок-объект, но с именем "logger".
            // Примечание: в обычных тестах можно обойтись @MockBean,
            // но тут ДЕМОНСТРИРУЕТСЯ схема работы ГЕНЕРАЦИИ бинов в Spring Boot
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
        // Проверка, что MOCK logger создался в Spring контексте
        // Он помечен как @Autowired, но создается в конфигурации TestConfig
        assertNotNull(logger);

        // test
        logger.info("Echo message: hello");

        // verify
        verify(logger, times(1)).info("Echo message: hello");
    }

    @Test
    public void testLoggerWithParameter() throws Exception {
        // Проверка, что MOCK logger создался в Spring контексте
        // Он помечен как @Autowired, но создается в конфигурации TestConfig
        assertNotNull(logger);

        String message = "hello";
        // test
        logger.info("Echo message: {}", message);

        // verify
        verify(logger, times(1)).info("Echo message: {}", message);
    }
}