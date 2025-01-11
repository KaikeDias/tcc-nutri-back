package com.tcc2.nutri_app_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc2.nutri_app_backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Patient extends User {
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "nutritionist_id", nullable = false)
    private Nutritionist nutritionist;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Menu menu;

    public Patient(String username, String password, String email, String phone, String cpf, Nutritionist nutritionist) {
        super(username, password, email, phone, cpf, Role.PATIENT);
        this.nutritionist = nutritionist;
    }

    public Patient() {
        super(null, null, null, null, null, Role.PATIENT);
    }
}
