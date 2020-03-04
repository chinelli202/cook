package guru.springframework.recipe.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.IngredientService;
import guru.springframework.recipe.services.RecipeService;
import guru.springframework.recipe.services.UnitOfMeasureService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static org.mockito.Mockito.*;


public class IngredientControllerTest {

	@Mock
	private RecipeService recipeService;
	
	@Mock
	private IngredientService ingredientService;
	
	@Mock
	private UnitOfMeasureService unitOfMeasureService;
	
	MockMvc mockMvc;
	
	private IngredientController ingredientController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}
	
	@Test
	public void testListIngredients() throws Exception {
		//given
		Recipe recipe = new Recipe();
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		//when
		mockMvc.perform(get("/recipe/1/ingredients"))
					.andExpect(status().isOk())
					.andExpect(view().name("recipe/ingredient/list"))
					.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeService, times(1)).findById(anyLong());
	}
	
	@Test
	public void testShowIngredient() throws Exception {
		//given
		Ingredient ingredient = new Ingredient();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredient);
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/show"))
		.andExpect(model().attributeExists("ingredient"));
	}
	
	@Test
	public void testShowIngredientForm() throws Exception {
		//given
		Ingredient ingredient = new Ingredient();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredient);
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientForm"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
	}
	
	@Test 
	public void testSaveIngredient() throws Exception {
		//given ingredient, parentId
		Ingredient ingredient = new Ingredient();
		ingredient.setId(3L);
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		ingredient.setRecipe(recipe);
		
		//when  savemethod invocation
		when(ingredientService.saveOrUpdate(any(), anyLong())).thenReturn(ingredient);
		
		
		//then  redirection, path
		mockMvc.perform(post("/recipe/1/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string")
		)
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/1/ingredient/3/show"));
	}
	
	@Test
	public void testNewIngredient() throws Exception {
		//new ingredient. url /recipe/id/ingredient/new. posted at recipe/id/ingredient. redirected at, get new Id. 
		//given
		Ingredient ingredient = new Ingredient();
		
		
		//when
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet());
		
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/new"))
					.andExpect(status().isOk())
					.andExpect(view().name("recipe/ingredient/ingredientForm"));
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		
		//given
		
		//when
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/4/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/ingredients"));
		
		verify(ingredientService, times(1)).deleteByRecipeIdAndIngredientId(anyLong(), anyLong());
	}
	
	
}
