package com.tcc2.nutri_app_backend.entities.DTOs;

public record CreatePatientDTO(
        String username,
        String email,
        String password,
        String name,
        String phone,
        String cpf
) {
}
