package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.MealDTO;
import com.tcc2.nutri_app_backend.entities.Food;
import com.tcc2.nutri_app_backend.entities.Meal;
import com.tcc2.nutri_app_backend.entities.Menu;
import com.tcc2.nutri_app_backend.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping("/{username}")
    public ResponseEntity createMenu(@PathVariable String username) {
        System.out.println("Create Menu");
        menuService.createMenu(username);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<Menu> getMenu(@PathVariable UUID menuId) {
        Menu menu = menuService.getById(menuId);

        return ResponseEntity.ok(menu);
    }

    @PostMapping("/{menuId}/meals")
    public ResponseEntity<Menu> addMealToMenu(
            @PathVariable String menuId,
            @RequestBody MealDTO data) {
        menuService.addMeal(data, UUID.fromString(menuId));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{menuId}/meals")
    public ResponseEntity<List<Meal>> getMeals(@PathVariable UUID menuId) {
        List<Meal> meals = menuService.getMeals(menuId);

        return ResponseEntity.ok(meals);
    }

    @GetMapping("/meals/{foodID}/substitutions")
    public ResponseEntity<List<Food>> getSubstitutions(@PathVariable UUID foodID) {
        List<Food> substitutions = menuService.getSubstitutions(foodID);

        return ResponseEntity.ok(substitutions);
    }
}
