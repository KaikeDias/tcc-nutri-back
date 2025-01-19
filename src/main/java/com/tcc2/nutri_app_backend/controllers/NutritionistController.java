package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.CreatePatientDTO;
import com.tcc2.nutri_app_backend.entities.DTOs.NutritionistDTO;
import com.tcc2.nutri_app_backend.entities.DTOs.PatientDTO;
import com.tcc2.nutri_app_backend.entities.Nutritionist;
import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.services.NutritionistService;
import com.tcc2.nutri_app_backend.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity registerPatient(@RequestBody @Valid CreatePatientDTO data) {
        var authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = authenticatedUser.getUsername();

        patientService.createPatient(data, username);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getByUsername")
    public ResponseEntity<Nutritionist> getNutritionistByUsername(@RequestParam String username) {
        return ResponseEntity.ok(nutritionistService.getNutritionistByUsername(username));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> getPatients() {
        var authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = authenticatedUser.getUsername();

        List<PatientDTO> patients = nutritionistService.findPatientsByNutritionist(username);

        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/patients")
    public ResponseEntity editPatient(@RequestBody @Valid PatientDTO data) {
        patientService.editPatient(data);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable UUID id) {
        Patient patient = patientService.getPatientById(id);
        PatientDTO patientDTO = patientService.convertPatientToDTO(patient);

        return ResponseEntity.ok(patientDTO);
    }
}
