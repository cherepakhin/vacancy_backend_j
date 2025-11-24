package ru.perm.v.vacancy_j.rest;

import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/echo")
@CrossOrigin(origins = "*")
@Tag(name = "Echo REST controller", description = "Controller for Echo test") // Tag для группировки (на пример все GET запросы в одной секции)
public class EchoRest {
    Logger log = LoggerFactory.getLogger(EchoRest.class);
    @GetMapping("/{message}")
    @Operation(summary = "Echo controller", description = "Позволяет проверить REST сервис")
    public String echo(
            @Parameter(description = "Тестовое сообщение", required = true)
            @PathVariable 
            @Size(max = 20, message = "Длина сообщения не должна превышать 20 символов")
            String message) {
        log.info("Echo message: {}", message);
        return message;
    }
}
