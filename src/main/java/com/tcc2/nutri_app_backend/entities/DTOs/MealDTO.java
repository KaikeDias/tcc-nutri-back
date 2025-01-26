package com.tcc2.nutri_app_backend.entities.DTOs;

import java.time.LocalTime;
import java.util.List;

public record MealDTO(
        String title,
        LocalTime mealTime,
        List<FoodDTO> aliments
) {
}
