package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.NutritionistDTO;
import com.tcc2.nutri_app_backend.entities.DTOs.PatientDTO;
import com.tcc2.nutri_app_backend.entities.Nutritionist;
import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.services.NutritionistService;
import com.tcc2.nutri_app_backend.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nutritionists")
public class NutritionistController {
    @Autowired
    NutritionistService nutritionistService;
    @Autowired
    PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity registerNutritionist(@RequestBody @Valid NutritionistDTO data) {
        nutritionistService.createNutritionist(data);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/createPatient")
    public ResponseEntity registerPatient(@RequestBody @Valid PatientDTO data) {
        var authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = authenticatedUser.getUsername();

        patientService.createPatient(data, username);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByUsername")
    public ResponseEntity<Nutritionist> getNutritionistByUsername(@RequestParam String username) {
        return ResponseEntity.ok(nutritionistService.getNutritionistByUsername(username));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getPatients() {
        var authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = authenticatedUser.getUsername();

        List<Patient> patients = nutritionistService.findPatientsByNutritionist(username);

        return ResponseEntity.ok(patients);
    }
}
