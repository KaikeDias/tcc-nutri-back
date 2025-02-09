package com.tcc2.nutri_app_backend.entities.DTOs;

import java.util.List;

public record FoodDTO(
        String name,
        String quantity,
        String unit,
        String homeQuantity,
        String homeUnit,
        List<FoodDTO> substitutions
) {
}
