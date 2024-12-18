package com.tcc2.nutri_app_backend.entities.DTOs;

import com.tcc2.nutri_app_backend.enums.Role;

public record NutritionistDTO (
        String username,
        String email,
        String password,
        String name,
        String phone,
        String cpf,
        String crn
) {
}
