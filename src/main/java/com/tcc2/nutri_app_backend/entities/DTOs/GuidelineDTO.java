package com.tcc2.nutri_app_backend.entities.DTOs;

import java.util.UUID;

public record GuidelineDTO(
        String title,
        String content,
        UUID patientId
) {
}
