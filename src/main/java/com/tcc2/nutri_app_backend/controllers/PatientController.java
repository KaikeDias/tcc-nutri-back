package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{username}")
    public ResponseEntity<Patient> getPatient(@PathVariable String username) {
        Patient patient = patientService.getPatientByUsername(username);

        return ResponseEntity.ok(patient);
    }
}
