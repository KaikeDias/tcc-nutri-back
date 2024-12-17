package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.NutritionistDTO;
import com.tcc2.nutri_app_backend.services.NutritionistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nutritionists")
public class NutritionistController {
    @Autowired
    NutritionistService nutritionistService;

    @PostMapping("/register")
    public ResponseEntity registerNutritionist(@RequestBody @Valid NutritionistDTO data) {
        nutritionistService.createNutritionist(data);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByUsername")
    public ResponseEntity getNutritionistByUsername(@RequestParam String username) {
        return ResponseEntity.ok(nutritionistService.getNutritionistByUsername(username));
    }
}
