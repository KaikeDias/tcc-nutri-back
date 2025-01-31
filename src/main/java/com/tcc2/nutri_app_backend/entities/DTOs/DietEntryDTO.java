package com.tcc2.nutri_app_backend.entities.DTOs;

import java.time.LocalDate;
import java.util.List;

public record DietEntryDTO(
        List<String> aliments,
        LocalDate date
) {
}
