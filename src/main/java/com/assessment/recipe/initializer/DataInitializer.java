package com.assessment.recipe.initializer;

import com.assessment.recipe.model.Recipe;
import com.assessment.recipe.repository.RecipeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.DeserializationFeature;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (recipeRepository.count() == 0) {
            ClassPathResource resource = new ClassPathResource("recipes.json");
            if (!resource.exists()) {
                System.out.println("recipes.json not found in classpath. Skipping initialization.");
                return;
            }
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<Map<String, Recipe>> typeReference = new TypeReference<Map<String, Recipe>>(){};
            try (InputStream inputStream = resource.getInputStream()) {
                Map<String, Recipe> recipeMap = mapper.readValue(inputStream, typeReference);
                List<Recipe> recipes = new ArrayList<>(recipeMap.values());
                for (Recipe recipe : recipes) {
                    if (recipe.getTitle() == null || recipe.getTitle().trim().isEmpty()) {
                        recipe.setTitle("Untitled Recipe");
                    }
                    if (recipe.getPrepTime() == null) {
                        recipe.setPrepTime(0);
                    }
                    if (recipe.getCookTime() == null) {
                        recipe.setCookTime(0);
                    }
                    if (recipe.getTotalTime() == null || recipe.getTotalTime() == 0) {
                        int prep = recipe.getPrepTime();
                        int cook = recipe.getCookTime();
                        recipe.setTotalTime(prep + cook);
                    }
                }
                recipeRepository.saveAll(recipes);
                System.out.println("Recipes Saved to Database from JSON file!");
            } catch (Exception e){
                System.out.println("Unable to save recipes: " + e.getMessage());
            }
        }
    }
}
