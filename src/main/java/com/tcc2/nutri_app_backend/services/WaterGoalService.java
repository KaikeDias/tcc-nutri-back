package com.tcc2.nutri_app_backend.services;

import com.tcc2.nutri_app_backend.entities.DTOs.WaterGoalDTO;
import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.entities.WaterGoal;
import com.tcc2.nutri_app_backend.repositories.WaterGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class WaterGoalService {
    @Autowired
    private WaterGoalRepository waterGoalRepository;
    @Autowired
    private PatientService patientService;

    public void createWaterGoal(WaterGoalDTO dto, UUID patientId) {
        Patient patient = patientService.getPatientById(patientId);
        WaterGoal waterGoal = new WaterGoal();
        waterGoal.setPatient(patient);
        waterGoal.setGoal(dto.goal());

        waterGoalRepository.save(waterGoal);
    }

    public WaterGoal getWaterGoal(UUID waterGoalId) {
        WaterGoal waterGoal = waterGoalRepository.findById(waterGoalId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Water goal not found"));

        return waterGoal;
    }

    public void updateWaterGoal(UUID waterGoalId, WaterGoalDTO dto) {
        WaterGoal waterGoal = getWaterGoal(waterGoalId);
        waterGoal.setGoal(dto.goal());
        waterGoalRepository.save(waterGoal);
    }
}
