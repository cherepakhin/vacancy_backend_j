package ru.perm.v.vacancy_j.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/echo")
@CrossOrigin(origins = "*")
//@Tag(name = "Echo REST controller", description = "Controller for Echo test")
public class EchoRest {
    Logger log = LoggerFactory.getLogger(EchoRest.class);
    @GetMapping("/{message}")
//    @Operation(summary = "Echo controller", description = "Позволяет проверить REST сервис")
    // @Parameter(description = "Тестовое сообщение", required = true)
    public String echo( @PathVariable String message) {
        log.info("Echo message: {}", message);
        return message;
    }
}
