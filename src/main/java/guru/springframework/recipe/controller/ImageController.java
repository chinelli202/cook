package guru.springframework.recipe.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.ImageService;
import guru.springframework.recipe.services.RecipeService;

@Controller
public class ImageController {

	
	ImageService imageService;
	
	RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		super();
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	
	@RequestMapping("recipe/{recipeId}/image")
	public String changeImageForm(@PathVariable String recipeId, Model model) {
		
		Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
		
		model.addAttribute("recipe", recipe);
		
		return "recipe/imageForm";
	}
	
	@PostMapping("recipe/{recipeId}/image")
	public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
		
		imageService.saveImageFile(Long.valueOf(recipeId), file);
		
		return "redirect:/recipe/"+ recipeId + "/show"; 
	}
	
	@RequestMapping("recipe/{recipeId}/recipeimage")
	public void displayImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
			Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
			
			response.setContentType("image/jpg");
			InputStream is = new ByteArrayInputStream(recipe.getImage());
			IOUtils.copy(is, response.getOutputStream());
	}
}
