package com.assessment.recipe.controller;

import com.assessment.recipe.model.Recipe;
import com.assessment.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping
    public ResponseEntity<?> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return ResponseEntity.ok(recipes);
    }

    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe) {
        List<String> missingFields = new ArrayList<>();
        if (recipe.getTitle() == null) missingFields.add("title");
        if (recipe.getCuisine() == null) missingFields.add("cuisine");
        if (recipe.getPrepTime() == null) missingFields.add("prep_time");
        if (recipe.getCookTime() == null) missingFields.add("cook_time");

        if (!missingFields.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", String.join(", ", missingFields) + " must be present in the request body.");
            return ResponseEntity.badRequest().body(error);
        }
        
        Recipe savedRecipe = recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopRecipes(@RequestParam(defaultValue = "5") int limit) {
        List<Recipe> topRecipes = recipeRepository.findTopRecipes(PageRequest.of(0, limit));
        Map<String, Object> response = new HashMap<>();
        response.put("data", topRecipes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getRecipeCount() {
        long count = recipeRepository.count();
        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
}
