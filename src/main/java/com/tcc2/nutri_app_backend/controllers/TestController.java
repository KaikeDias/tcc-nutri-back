package com.tcc2.nutri_app_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TestController {
    @GetMapping()
    public ResponseEntity<String> testeGet() {
        return ResponseEntity.ok("Hello World");
    }
}
