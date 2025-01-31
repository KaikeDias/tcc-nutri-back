package com.tcc2.nutri_app_backend.entities.DTOs;

import java.util.UUID;

public record DietEntryResponseDTO(
        UUID id,
        String title
) {
}
