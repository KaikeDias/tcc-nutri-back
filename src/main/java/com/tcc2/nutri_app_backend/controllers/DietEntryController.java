package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.DietEntryDTO;
import com.tcc2.nutri_app_backend.entities.DTOs.DietEntryResponseDTO;
import com.tcc2.nutri_app_backend.entities.DietEntry;
import com.tcc2.nutri_app_backend.services.DietEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/dietEntries")
public class DietEntryController {
    @Autowired
    private DietEntryService dietEntryService;

    @PostMapping("/patients/{patientId}/meals/{mealId}")
    public ResponseEntity<DietEntry> createDietEntry(
            @PathVariable UUID patientId,
            @PathVariable UUID mealId,
            @RequestParam List<String> aliments,
            @RequestParam LocalDate date,
            @RequestParam MultipartFile photo
    ) {
        try {
            DietEntry dietEntry = dietEntryService.save(aliments, date, patientId, mealId, photo);

            return ResponseEntity.status(HttpStatus.CREATED).body(dietEntry);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<Map<LocalDate, List<DietEntryResponseDTO>> > getDietEntriesByPatientId(@PathVariable UUID patientId) {
        Map<LocalDate, List<DietEntryResponseDTO>> dietEntries = dietEntryService.getDietEntriesByPatientId(patientId);
        return ResponseEntity.ok(dietEntries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietEntry> getDietEntryById(@PathVariable UUID id) {
        DietEntry dietEntry = dietEntryService.getDietEntryById(id);

        return ResponseEntity.ok(dietEntry);
    }

}
