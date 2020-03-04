package guru.springframework.recipe.services;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

	
	private ImageServiceImpl imageServiceImpl;
	
	@Mock
	RecipeRepository recipeRepository;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		imageServiceImpl = new ImageServiceImpl(recipeRepository);
	}
	
	@Test
	public void saveImageFile() throws IOException {
		//given
		
		MockMultipartFile multipartFile = new MockMultipartFile("imagefile","testing.txt", "text/plain", 
				"Spring Framework Guru".getBytes());
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class); 
		
		//when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		
		imageServiceImpl.saveImageFile(recipe.getId(), multipartFile);
		
		//then : verify invocation of save method on recipeRepository, 
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		
		Recipe recipeCaptured = argumentCaptor.getValue();
		assertEquals(recipeCaptured.getImage().length, multipartFile.getBytes().length);
	}
}
