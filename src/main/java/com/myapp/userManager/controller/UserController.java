package com.myapp.userManager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/saludar")
    public String obtenerSaludo() {
        return "HOLAAAA";
    }

    @PostMapping("/user/auth")
    public ResponseEntity<Object> createUser() {
        return new ResponseEntity<>("Hola RE", HttpStatus.OK);
    }
}
