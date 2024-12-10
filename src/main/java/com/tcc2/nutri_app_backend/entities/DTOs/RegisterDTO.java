package com.tcc2.nutri_app_backend.entities.DTOs;

import com.tcc2.nutri_app_backend.enums.Role;

public record RegisterDTO(
        String username,
        String email,
        String password,
        Role role
) {
}
