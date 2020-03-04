package guru.springframework.recipe.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.services.IngredientService;
import guru.springframework.recipe.services.RecipeService;
import guru.springframework.recipe.services.UnitOfMeasureService;

@Controller
public class IngredientController {

	private RecipeService recipeService;
	private IngredientService ingredientService;
	private UnitOfMeasureService unitOfMeasureService;
	

	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}
	
	
	@RequestMapping("recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
		
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		Ingredient ingredient = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id));
		
		model.addAttribute("ingredient",ingredient);
		return "recipe/ingredient/show";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		
		Ingredient ingredient = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		
		//add uom List
		Set<UnitOfMeasure> uomList = unitOfMeasureService.listAllUoms();
		
		model.addAttribute("ingredient", ingredient);
		model.addAttribute("uomList", uomList);
		
		return "recipe/ingredient/ingredientForm";
	}
	
	/*@PostMapping
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdateIngredient(@ModelAttribute Ingredient ingredient, Model model) {
		
		Ingredient savedIngredient = ingredientService.save(ingredient);
		model.addAttribute("ingredient", savedIngredient);
		
		//
		return "recipe/ingredient/list";
	}*/
	
	@PostMapping
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdateIngredient(@ModelAttribute Ingredient ingredient, @PathVariable String recipeId, Model model) {
		
		Ingredient savedIngredient = ingredientService.saveOrUpdate(ingredient, Long.valueOf(recipeId));
		//model.addAttribute("ingredient", savedIngredient);
		
		//
		return "redirect:/recipe/" + savedIngredient.getRecipe().getId() + "/ingredient/" + savedIngredient.getId() + "/show";
	}
	
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		Set<UnitOfMeasure> uomList = unitOfMeasureService.listAllUoms();
		
		Ingredient ingredient = new Ingredient();
		Recipe recipe = new Recipe();
		recipe.setId(Long.valueOf(recipeId));
		ingredient.setRecipe(recipe);
		ingredient.setUom(new UnitOfMeasure()
				);
		
		
		model.addAttribute("uomList", uomList);
		model.addAttribute("ingredient", ingredient);
		
		return "recipe/ingredient/ingredientForm";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
		
		ingredientService.deleteByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		
		return "redirect:/recipe/" + recipeId + "/ingredients"; 
				
				
	}
	
}
