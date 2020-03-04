package guru.springframework.recipe.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

	RecipeRepository recipeRepository;
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	
	
	public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}



	@Override
	public Ingredient findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		// TODO Auto-generated method stub
		Optional<Recipe> recipe = recipeRepository.findById(recipeId);
		
		
		//explain this
		if(!recipe.isPresent()) {
			throw new RuntimeException();
		}
		
		Optional<Ingredient> ingredient = recipe.get().getIngredients().stream()
				.filter(ing -> ing.getId().equals(ingredientId)).findFirst();
		if(!ingredient.isPresent()) {
			throw new RuntimeException();
		}
		
		return ingredient.get();
	}



	@Override
	public Ingredient saveOrUpdate(Ingredient ingredient, Long recipeId) {
		// TODO Auto-generated method stub
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if(!recipeOptional.isPresent()) {
			throw new RuntimeException("Error Loading Parent Recipe");
		}
		else {
			Recipe parent = recipeOptional.get();
			Optional<Ingredient> ingredientOptional = parent.getIngredients().stream()
					.filter(ingt -> ingt.getId().equals(ingredient.getId()))
					.findFirst();
			if(ingredientOptional.isPresent()) {
				Ingredient foundIngredient = ingredientOptional.get();
				foundIngredient.setAmount(ingredient.getAmount());
				foundIngredient.setDescription(ingredient.getDescription());
				foundIngredient.setUom(unitOfMeasureRepository.findById(ingredient.getUom().getId())
						.orElseThrow(() -> new RuntimeException()));
				
			}
			else {
				ingredient.setRecipe(parent);
				parent.getIngredients().add(ingredient);
			}
			
			Recipe savedRecipe = recipeRepository.save(parent);
			
			//filter
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(ing -> ing.getId().equals(ingredient.getId()))
					.findFirst();
			if(savedIngredientOptional.isPresent()) {
				return savedIngredientOptional.get();
			}
			
			else {
				return savedRecipe.getIngredients().stream()
						.filter(ing -> ing.getDescription().equals(ingredient.getDescription()))
						.filter(ing ->ing.getAmount().equals(ingredient.getAmount()))
						.filter(ing -> ing.getUom().getId().equals(ingredient.getUom().getId()))
						.findFirst().get();				
			}
		}
	}



	@Override
	public Ingredient saveOrUpdate(Ingredient ingredient) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		// TODO Auto-generated method stub
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()) {
			throw new RuntimeException("recipe not found for given id");
		}
		else {
			Recipe recipe = recipeOptional.get();
			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ing -> ing.getId().equals(ingredientId)).findFirst();
			if(!ingredientOptional.isPresent()) {
				throw new RuntimeException("ingredient not found in recipe for given id");
			}
			else {
				Ingredient ingredient = ingredientOptional.get();
				ingredient.setRecipe(null);
				recipe.getIngredients().remove(ingredient);
				recipeRepository.save(recipe);
			}
		}
	}
}
