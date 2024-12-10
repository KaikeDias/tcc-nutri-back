package com.tcc2.nutri_app_backend.entities;

import com.tcc2.nutri_app_backend.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Nutritionist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private String phone;
    private String cpf;
    private String crn;

    @OneToMany(mappedBy = "nutritionist")
    private List<Patient> patients;
}
