package guru.springframework.recipe.services;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

	@Mock
	private RecipeRepository recipeRepository;
	
	@Mock 
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	IngredientService ingredientService;
	
	@Before
	public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository);
	}
	
	@Test
	public void findByRecipeIdAndIdHappyPath() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//when
		Ingredient foundIngredient = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
		
		
		//then
		assertEquals(Long.valueOf(1L), foundIngredient.getRecipe().getId());
		assertEquals(Long.valueOf(3L), foundIngredient.getId());
		verify(recipeRepository, times(1)).findById(anyLong());	
	}
	
	@Test
	public void testSaveOrUpdate() {
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(3L);
		ingredient.setDescription("oinion");
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.setId(1L);
		savedRecipe.getIngredients().add(ingredient);
		
		
		
		//when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		ingredientService.saveOrUpdate(ingredient,1L);
		
		
		//then
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any());
	}
	
	@Test
	public void testDeleteIngredient() {
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(2L);
		
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		recipe.getIngredients().add(ingredient);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		//when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(recipe);
		
		ingredientService.deleteByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId());
		
		//then
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any());
		
	}
	
}
