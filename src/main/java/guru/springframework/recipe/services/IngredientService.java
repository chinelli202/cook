package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Ingredient;

public interface IngredientService {
	
	public Ingredient findByRecipeIdAndIngredientId(Long recipeId, Long id);
	public Ingredient saveOrUpdate(Ingredient ingredient, Long recipeId);
	public Ingredient saveOrUpdate(Ingredient ingredient);
	public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

}
