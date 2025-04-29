package ru.perm.v.vacancy_j.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/echo")
@CrossOrigin(origins = "*")
public class EchoRest {
    Logger log = LoggerFactory.getLogger(EchoRest.class);
    @GetMapping("/{message}")
    public String echo(@PathVariable String message) {
        log.info("Echo message: {}", message);
        return message;
    }
}
