package ru.perm.v.vacancy_j.conf;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    private Environment environment;

    public OpenAPIConfiguration(@Autowired Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI defineOpenAPI () {
        Server server = new Server();
        String serverUrl = "https://v.perm.ru/api";
        server.setUrl(serverUrl);
        server.setDescription("Development");

        Info info = new Info()
                .title("API для работы вакансиями.")
                .version("1.0")
                .description("API предоставляет REST сервисы для работы с вакансиями.");
        return new OpenAPI().info(info).servers(List.of(server));
    }
}
