package com.cienmilsabores.backendcienmil.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;


@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/ping")
    public String ping() {
        return "Spring Boot funcionando - " + new Date();
    }
}
//Comando para ejectuar spring boot es .\mvnw spring-boot:run