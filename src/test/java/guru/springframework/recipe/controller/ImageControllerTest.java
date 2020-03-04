package guru.springframework.recipe.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.ImageService;
import guru.springframework.recipe.services.RecipeService;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ImageControllerTest {

	
	@Mock
	ImageService imageService;
	
	@Mock
	RecipeService recipeService;
	
	ImageController imageController;
	
	MockMvc mockMvc;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		imageController = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
	}
	
	@Test
	public void getImageForm() throws Exception {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		
		//when
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		
		//then
		mockMvc.perform(get("/recipe/1/image"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/imageForm"));
		
		verify(recipeService, times(1)).findById(anyLong());
		
	}
	
	@Test
	public void handleImageSubmit() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("imagefile","testing.txt", "text/plain", 
				"Spring Framework Guru".getBytes());
		
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));
		
		verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}
	
	@Test
	public void displayImageFromDB() throws Exception {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		MockMultipartFile multipartFile = new MockMultipartFile("imagefile","testing.txt", "text/plain", 
				"Spring Framework Guru".getBytes());
		
		byte[] imageData = multipartFile.getBytes();
		recipe.setImage(imageData);
		
		//when
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		//then
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk()).andReturn().getResponse();
		
		byte[] data = response.getContentAsByteArray();
		assertEquals(data.length, multipartFile.getBytes().length);
		
	}
}
