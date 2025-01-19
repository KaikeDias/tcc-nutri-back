package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.GuidelineDTO;
import com.tcc2.nutri_app_backend.entities.Guideline;
import com.tcc2.nutri_app_backend.services.GuidelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guidelines")
public class GuidelineController {
    @Autowired
    private GuidelineService guidelineService;

    @PostMapping
    public ResponseEntity addGuideline(@RequestBody GuidelineDTO data) {
        guidelineService.createGuideline(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<Guideline>> getGuidelinesByPatientId(@PathVariable UUID patientId) {
        List<Guideline> guidelines = guidelineService.getAllGuidelinesByPatient(patientId);

        return ResponseEntity.ok(guidelines);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateGuideline(@PathVariable UUID id, @RequestBody GuidelineDTO data) {
        guidelineService.editGuideline(id, data);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGuideline(@PathVariable UUID id) {
        guidelineService.deleteGuideline(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
