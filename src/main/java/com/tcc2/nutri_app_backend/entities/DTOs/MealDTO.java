package com.tcc2.nutri_app_backend.entities.DTOs;

import java.util.List;

public record MealDTO(
        String title,
        List<FoodDTO> aliments
) {
}
