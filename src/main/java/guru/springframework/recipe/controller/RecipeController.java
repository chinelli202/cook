package guru.springframework.recipe.controller;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;

@Controller
public class RecipeController {

	
	RecipeService recipeService; 
		
	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}


	@RequestMapping("/recipe/{id}/show")
	public String showViews(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		
		return "recipe/show";
	}
	
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		
		model.addAttribute("recipe", new Recipe());
		
		return "/recipe/recipeForm";
	}
	
	
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		return "recipe/recipeForm";
	}
	
	@PostMapping
	@RequestMapping("recipe")
	public String saveOrUpdate(@ModelAttribute Recipe recipe) {
		
		//save then redirect
		Recipe savedRecipe = recipeService.save(recipe);
		return "redirect:/recipe/" + savedRecipe.getId() + "/show" ; 
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		
		recipeService.deleteById(Long.valueOf(id));
		return "redirect:/";
	}
}
