package com.tcc2.nutri_app_backend.services;

import com.tcc2.nutri_app_backend.entities.DTOs.CreatePatientDTO;
import com.tcc2.nutri_app_backend.entities.DTOs.PatientDTO;
import com.tcc2.nutri_app_backend.entities.Nutritionist;
import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.repositories.PatientRepository;
import com.tcc2.nutri_app_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NutritionistService nutritionistService;

    public void createPatient(CreatePatientDTO patientDTO, String username) {
        if(userRepository.findByUsername(patientDTO.username()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
        }

        Nutritionist nutritionist = nutritionistService.getNutritionistByUsername(username);
        String encryptedPassword = new BCryptPasswordEncoder().encode(patientDTO.password());

        Patient patient = new Patient();
        patient.setNutritionist(nutritionist);
        patient.setUsername(patientDTO.username());
        patient.setPassword(encryptedPassword);
        patient.setEmail(patientDTO.email());
        patient.setName(patientDTO.name());
        patient.setPhone(patientDTO.phone());
        patient.setCpf(patientDTO.cpf());

        patientRepository.save(patient);
    }

    public Patient getPatientByUsername(String username) {
        Patient patient = patientRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found"));

        return patient;
    }

    public Patient getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        return patient;
    }

    public PatientDTO convertPatientToDTO(Patient patient) {
        PatientDTO dto = new PatientDTO(
                patient.getId().toString(),
                patient.getUsername(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getName(),
                patient.getPhone(),
                patient.getCpf(),
                patient.getMenu().getId().toString(),
                patient.getForm().getId().toString(),
                patient.getWaterGoal().getId().toString()
        );

        return dto;
    }

    public void editPatient(PatientDTO patientDTO) {
        Patient patient = getPatientById(UUID.fromString(patientDTO.id()));

        patient.setUsername(patientDTO.username());
        patient.setEmail(patientDTO.email());
        patient.setName(patientDTO.name());
        patient.setPhone(patientDTO.phone());
        patient.setCpf(patientDTO.cpf());

        patientRepository.save(patient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
