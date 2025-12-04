package ru.perm.v.vacancy_j.conf;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadConfTest {
    @Test
    public void readFromFileTest() throws IOException {
        ClassPathResource resource = new ClassPathResource("job_sites.csv");
        List<String> lines = resource.getContentAsString(StandardCharsets.UTF_8).lines().toList();


        assertEquals(3, lines.size());
        assertEquals("hh.ru;https://hh.ru", lines.get(0));
        assertEquals("trudvsem.ru;https://trudvsem.ru", lines.get(1));
        assertEquals("v.perm.ru;https://v.perm.ru", lines.get(2));
    }

    @Test
    public void splitTest() throws IOException {
        String resource = "hh.ru;https://hh.ru";
        String[] fields = resource.split(";");

        assertEquals(2, fields.length);
        assertEquals("hh.ru", fields[0]);
        assertEquals("https://hh.ru", fields[1]);
    }
}
