package com.tcc2.nutri_app_backend.services;

import com.tcc2.nutri_app_backend.entities.DTOs.NutritionistDTO;
import com.tcc2.nutri_app_backend.entities.Nutritionist;
import com.tcc2.nutri_app_backend.repositories.NutritionistRepository;
import com.tcc2.nutri_app_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NutritionistService {

    @Autowired
    private NutritionistRepository nutritionistRepository;
    @Autowired
    private UserRepository userRepository;

    public void createNutritionist(NutritionistDTO nutritionistDTO) {
        if(userRepository.findByUsername(nutritionistDTO.username()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(nutritionistDTO.password());

        Nutritionist newNutritionist = new Nutritionist();
        newNutritionist.setUsername(nutritionistDTO.username());
        newNutritionist.setPassword(encryptedPassword);
        newNutritionist.setEmail(nutritionistDTO.email());
        newNutritionist.setName(nutritionistDTO.name());
        newNutritionist.setPhone(nutritionistDTO.phone());
        newNutritionist.setCpf(nutritionistDTO.cpf());
        newNutritionist.setCrn(nutritionistDTO.crn());

        nutritionistRepository.save(newNutritionist);
    }

    public Nutritionist getNutritionistByUsername(String username) {
        Nutritionist nutritionist = nutritionistRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found"));

        return nutritionist;
    }
}
