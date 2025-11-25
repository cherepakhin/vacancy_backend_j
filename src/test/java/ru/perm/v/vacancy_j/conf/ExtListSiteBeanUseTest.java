package ru.perm.v.vacancy_j.conf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perm.v.vacancy_j.dto.ExtSiteDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест использования конфигурации списка сайтов
 */
@SpringBootTest
public class ExtListSiteBeanUseTest {
    // Так можно получить список сайтов из конфигурации как Bean
    @Autowired
    private List<ExtSiteDto> listExtSiteDto;

    @Test
    public void testConfig() {
        assert listExtSiteDto.size() == 3;
    }

    @Test
    public void checkContent() {
        assertEquals(new ExtSiteDto("hh.ru","https://hh.ru"), listExtSiteDto.get(0));
        assertEquals(new ExtSiteDto("trudvsem.ru","https://trudvsem.ru"), listExtSiteDto.get(1));
        assertEquals(new ExtSiteDto("v.perm.ru","https://v.perm.ru"), listExtSiteDto.get(2));
    }
}
