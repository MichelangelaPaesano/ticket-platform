package com.example.ticket_platform.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Authentication auth, Model model) {
        // serve per stampare in console tutte le authority dell'utente
        /*System.out.println("Utente loggato: " + auth.getName());
        auth.getAuthorities().forEach(a -> 
            System.out.println("Authority reale: [" + a.getAuthority() + "]")
        );*/

        // Invio i dati a thymeleaf
        model.addAttribute("username", auth.getName());
        model.addAttribute("authorities", auth.getAuthorities());

        return "index"; 
    }
}
