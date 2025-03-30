package com.example.restauranteAPI.controle;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping
    public String home() {
        return "Aplicação REST está funcionando!";
    }
}
