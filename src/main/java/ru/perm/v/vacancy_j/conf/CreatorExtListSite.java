package ru.perm.v.vacancy_j.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.perm.v.vacancy_j.dto.ExtSiteDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CreatorExtListSite {
    private static final String EXT_SITES_FILE = "ext_sites.csv";
    Logger logger = LoggerFactory.getLogger(CreatorExtListSite.class);
    @Bean
    public List<ExtSiteDto> extListSite() throws IOException {
        ClassPathResource resource = new ClassPathResource(EXT_SITES_FILE);
        List<String> lines = resource.getContentAsString(StandardCharsets.UTF_8).lines().toList();
        logger.info(lines.toString());
        List<ExtSiteDto> extSiteDtoList = new ArrayList<>();
        for (String line : lines) {
                logger.info("External site: " + line);
                extSiteDtoList.add(new ExtSiteDto(line));
        }
        logger.info(extSiteDtoList.toString());
        return extSiteDtoList;
    }
}
