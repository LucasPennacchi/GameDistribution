package com.gamedistribution.gamedistribution.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller de nível web para gerenciar a página inicial.
 */
@Controller
public class WebController {

    /**
     * Redireciona a requisição da raiz (/) para a página de login.
     * @return O nome do arquivo HTML (login.html).
     */
    @GetMapping("/")
    public String redirectToLogin() {
        return "login.html";
    }
}