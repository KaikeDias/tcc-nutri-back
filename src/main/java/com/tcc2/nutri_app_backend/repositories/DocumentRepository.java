package com.tcc2.nutri_app_backend.repositories;

import com.tcc2.nutri_app_backend.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findAllByPatientId(UUID patientId);
}
