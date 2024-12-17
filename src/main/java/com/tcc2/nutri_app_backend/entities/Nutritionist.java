package com.tcc2.nutri_app_backend.entities;

import com.tcc2.nutri_app_backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Nutritionist extends User {
    private String crn;

    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients;

    public Nutritionist(String username, String password, String email, String phone, String cpf, String crn) {
        super(username, password, email, phone, cpf, Role.NUTRITIONIST);
        this.crn = crn;
    }

    public Nutritionist() {
        super(null, null, null, null, null, Role.NUTRITIONIST); // ou você pode chamar o super com valores padrão
    }
}
