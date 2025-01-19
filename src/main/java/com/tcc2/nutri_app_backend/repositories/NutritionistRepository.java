package com.tcc2.nutri_app_backend.repositories;

import com.tcc2.nutri_app_backend.entities.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist, UUID> {
    Optional<Nutritionist> findByUsername(String username);
}
