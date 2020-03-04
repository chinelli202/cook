package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> findAll();
    Recipe findById(Long id);
    Recipe save(Recipe recipe);
    void deleteById(Long id);
}
