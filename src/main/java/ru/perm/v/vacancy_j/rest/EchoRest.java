package ru.perm.v.vacancy_j.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/echo")
@CrossOrigin(origins = "*")
public class EchoRest {
    @GetMapping("/{message}")
    public String echo(@PathVariable String message) {
        return message;
    }
}
