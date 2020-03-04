package guru.springframework.recipe.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;

@Service
public class ImageServiceImpl implements ImageService{

	RecipeRepository recipeRepository;
	
	

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}



	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		// TODO Auto-generated method stub
		
		//hibernate team best practice recommendation : use Byte wrapper. wont do that here though.  
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if(!recipeOptional.isPresent()) {
			throw new RuntimeException("recipe not found for given recipeId");
		}
		else {
			Recipe recipe =	 recipeOptional.get();
			
			
			
			try {
				//recommended hibernate way
				
				/*Byte[] byteObjects = new Byte[file.getBytes().length];
				int i  = 0;
				for(byte b: file.getBytes()) {
					byteObjects[i++] = b; 
				}*/
				
				
				byte[] data = file.getBytes();
				recipe.setImage(data);
				recipeRepository.save(recipe);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("received image save notification");
	}

}
