package ru.perm.v.vacancy_j.conf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perm.v.vacancy_j.dto.JobSiteDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест использования конфигурации списка сайтов
 */
@SpringBootTest
public class ListSiteBeanUseTest {
    // Так можно получить список сайтов из файла csv как Spring Bean
    // см. ru.perm.v.vacancy_j.conf.CreatorExtListSite.extListSite
    @Autowired
    private List<JobSiteDto> listSiteDto;

    @Test
    public void testConfig() {
        assertEquals(3, listSiteDto.size());
    }

    @Test
    public void checkContent() {
        assertEquals(new JobSiteDto("hh.ru","https://hh.ru"), listSiteDto.get(0));
        assertEquals(new JobSiteDto("trudvsem.ru","https://trudvsem.ru"), listSiteDto.get(1));
        assertEquals(new JobSiteDto("v.perm.ru","https://v.perm.ru"), listSiteDto.get(2));
    }
}
