package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.AuthenticationDTO;
import com.tcc2.nutri_app_backend.entities.DTOs.LoginResponseDTO;
import com.tcc2.nutri_app_backend.entities.DTOs.RegisterDTO;
import com.tcc2.nutri_app_backend.entities.User;
import com.tcc2.nutri_app_backend.infra.security.TokenService;
import com.tcc2.nutri_app_backend.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity registerNutritionist(@RequestBody @Valid RegisterDTO data) {
        if(this.repository.findByUsername(data.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), data.email(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
