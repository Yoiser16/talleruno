package com.lpados.talleruno.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }
    
    @GetMapping("/usuarios")
    public String usuarios() {
        return "usuarios";
    }
    
    @GetMapping("/libros")
    public String libros() {
        return "libros";
    }
    
    @GetMapping("/prestamos")
    public String prestamos() {
        return "prestamos";
    }
    
    @GetMapping("/consulta-usuarios")
    public String consultaUsuarios() {
        return "consulta-usuarios";
    }
}