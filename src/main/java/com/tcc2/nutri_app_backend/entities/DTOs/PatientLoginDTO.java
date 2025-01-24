package com.tcc2.nutri_app_backend.entities.DTOs;

public record PatientLoginDTO(
        String token,
        PatientDTO patientDTO
) {
}
