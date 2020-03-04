package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockAnnotationProcessor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    private RecipeServiceImpl service;

    @Mock
    RecipeRepository recipeRepository;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void findAll() {

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        when(recipeRepository.findAll()).thenReturn(recipes);
        Set<Recipe> returnedRecipes = service.findAll();
        assertEquals(1, recipes.size());
    }
    
    @Test
    public void findRecipeById() {
    	
    	Recipe specialRec = new Recipe();
    	specialRec.setId(2L);
    	
    	Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(specialRec));
    	Recipe returnRecipe = service.findById(2L);
    	
    	verify(recipeRepository).findById(2L);
    	assertEquals(returnRecipe.getId(), specialRec.getId());
    }
}