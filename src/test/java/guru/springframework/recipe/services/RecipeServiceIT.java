package guru.springframework.recipe.services;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	
	@Transactional
	@Test
	public void saveTest() {
		//given
		Iterator<Recipe> iterator = recipeService.findAll().iterator();
		Recipe recipe = iterator.next();
		
		//when
		recipe.setDescription("new recipe description");		
		Recipe savedRecipe = recipeRepository.save(recipe);		
		
		//then
		assertEquals(savedRecipe.getDescription(), recipe.getDescription());
	}	
}

