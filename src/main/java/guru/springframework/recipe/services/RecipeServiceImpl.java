package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> findAll() {
        Set<Recipe> resultSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(resultSet::add);
        return resultSet;
    }

	@Override
	public Recipe findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Recipe> recipe = recipeRepository.findById(id);
		
		
		//explain this
		if(!recipe.isPresent()) {
			throw new RuntimeException();
		}
		
		return recipe.get();
	}

	@Override
	public Recipe save(Recipe recipe) {
		// TODO Auto-generated method stub
		return recipeRepository.save(recipe);
	}

	@Override
	public void deleteById(Long id) {
		recipeRepository.deleteById(id);
	}
	
	
}
