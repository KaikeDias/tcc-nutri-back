package com.tcc2.nutri_app_backend.repositories;

import com.tcc2.nutri_app_backend.entities.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {
}
