package ru.perm.v.vacancy_j.conf;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.ExtSiteDto;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatorExtListSiteTest {
    @Test
    void readFromResource() throws IOException {
        CreatorExtListSite creatorExtListSite = new CreatorExtListSite();
        List<ExtSiteDto> sites = creatorExtListSite.extListSite();

        assertEquals(3, sites.size());
        assertEquals(new ExtSiteDto("hh.ru","https://hh.ru"), sites.get(0));
        assertEquals(new ExtSiteDto("trudvsem.ru","https://trudvsem.ru/"), sites.get(1));
        assertEquals(new ExtSiteDto("v.perm.ru","https://v.perm.ru"), sites.get(2));
    }

}