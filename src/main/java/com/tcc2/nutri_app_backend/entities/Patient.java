package com.tcc2.nutri_app_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Patient{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private String phone;
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "nutritionist_id", nullable = false)
    private Nutritionist nutritionist;
}
