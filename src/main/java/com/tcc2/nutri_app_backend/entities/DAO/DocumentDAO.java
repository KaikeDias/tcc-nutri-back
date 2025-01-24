package com.tcc2.nutri_app_backend.entities.DAO;

public record DocumentDAO(
        String id,
        String filename,
        String patientId,
        String nutritionistId,
        long size
) {
}
