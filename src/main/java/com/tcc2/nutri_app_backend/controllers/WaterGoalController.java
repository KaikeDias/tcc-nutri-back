package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.WaterGoalDTO;
import com.tcc2.nutri_app_backend.entities.WaterGoal;
import com.tcc2.nutri_app_backend.services.WaterGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/waterGoals")
public class WaterGoalController {
    @Autowired
    private WaterGoalService waterGoalService;

    @PostMapping("/patients/{patientId}")
    public ResponseEntity createWaterGoal(@PathVariable UUID patientId, @RequestBody WaterGoalDTO data) {
        waterGoalService.createWaterGoal(data, patientId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaterGoal> getWaterGoal(@PathVariable UUID id) {
        WaterGoal waterGoal = waterGoalService.getWaterGoal(id);

        return ResponseEntity.ok(waterGoal);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateWaterGoal(@PathVariable UUID id, @RequestBody WaterGoalDTO data) {
        waterGoalService.updateWaterGoal(id, data);

        return ResponseEntity.ok().build();
    }
}
