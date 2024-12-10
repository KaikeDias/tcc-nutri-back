package com.tcc2.nutri_app_backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class teste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
