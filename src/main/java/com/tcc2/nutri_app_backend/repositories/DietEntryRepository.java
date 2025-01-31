package com.tcc2.nutri_app_backend.repositories;

import com.tcc2.nutri_app_backend.entities.DietEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DietEntryRepository extends JpaRepository<DietEntry, UUID> {
    boolean existsByPatientIdAndMealIdAndDate(UUID patientId, UUID mealId, LocalDate date);
    List<DietEntry> findByPatientId(UUID patientId);
}
