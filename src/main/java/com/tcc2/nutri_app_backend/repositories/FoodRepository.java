package com.tcc2.nutri_app_backend.repositories;

import com.tcc2.nutri_app_backend.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID> {
}
