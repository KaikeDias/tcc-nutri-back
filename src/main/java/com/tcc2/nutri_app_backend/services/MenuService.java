package com.tcc2.nutri_app_backend.services;

import com.tcc2.nutri_app_backend.entities.DTOs.MealDTO;
import com.tcc2.nutri_app_backend.entities.Food;
import com.tcc2.nutri_app_backend.entities.Meal;
import com.tcc2.nutri_app_backend.entities.Menu;
import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.repositories.FoodRepository;
import com.tcc2.nutri_app_backend.repositories.MenuRepository;
import com.tcc2.nutri_app_backend.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private FoodRepository foodRepository;

    public void createMenu(String username) {
        Patient patient = patientService.getPatientByUsername(username);

        Menu menu = new Menu();
        menu.setPatient(patient);
        menuRepository.save(menu);
    }

    public Menu getById(UUID id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found"));
    }

    public void addMeal(MealDTO mealDTO, UUID menuID) {
        Menu menu = getById(menuID);
        List<Meal> meals = menu.getMeals();

        Meal newMeal = new Meal();
        newMeal.setTitle(mealDTO.title());

        List<Food> aliments = mealDTO.aliments().stream().map(alimentDTO -> {
            Food food = new Food();
            food.setName(alimentDTO.name());
            food.setQuantity(alimentDTO.quantity());
            food.setUnit(alimentDTO.unit());

            if (alimentDTO.substitutions() != null) {
                List<Food> substitutions = alimentDTO.substitutions().stream().map(substitutionDTO -> {
                    Food substitution = new Food();
                    substitution.setName(substitutionDTO.name());
                    substitution.setQuantity(substitutionDTO.quantity());
                    substitution.setUnit(substitutionDTO.unit());
                    return substitution;
                }).collect(Collectors.toList());
                food.setSubstitutions(substitutions);
            }

            return food;
        }).collect(Collectors.toList());

        newMeal.setAliments(aliments);
        meals.add(newMeal);

        menu.setMeals(meals);

        menuRepository.save(menu);
    }

    public List<Meal> getMeals(UUID menuID) {
        Menu menu = getById(menuID);

        return menu.getMeals();
    }

    public List<Food> getSubstitutions(UUID foodID) {
        Food food = foodRepository.findById(foodID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));

        return food.getSubstitutions();
    }
}
