package guru.springframework.recipe.bootstrap;

import guru.springframework.recipe.domain.Category;
import guru.springframework.recipe.domain.Difficulty;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Notes;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.repositories.CategoryRepository;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    CategoryRepository categoryRepository;
    RecipeRepository recipeRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    private List<Recipe> getRecipeList() {
        //create a recipe instance,
        // set all its attributes and properies
                //description, preptime, cooktime, servings, directions
        // create ingredients and attach unit of measure
        // add ingredients to recipe's ingredients list
        // persist recipe into database using the right repository

        Recipe guacamole = new Recipe();

        //load units of measure
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> dash = unitOfMeasureRepository.findByDescription("Dash");

        if(!teaspoon.isPresent()){
            throw new RuntimeException("Cant find unit of Measure : Teaspoon");
        }

        if(!tablespoon.isPresent()){
            throw new RuntimeException("Cant find unit of Measure : Tablespoon");
        }

        if(!dash.isPresent()){
            throw new RuntimeException("Cant find unit of Measure : Dash");
        }

        
        //loading categories
        Optional<Category> american = categoryRepository.findByDescription("American");
        
        if(!american.isPresent()) {
        	throw new RuntimeException("Can't find category : American");
        }
        
        Optional<Category> mexican = categoryRepository.findByDescription("Mexican");
        
        if(!mexican.isPresent()) {
        	throw new RuntimeException("Can't find category : Mexican");
        }
        
        guacamole.setDescription("How to Make Perfect Guacamole");
        guacamole.setCookTime(0);
        guacamole.setPrepTime(600);
        guacamole.setServings(4);
        guacamole.setSource("Simple Recipes");
        guacamole.setDifficulty(Difficulty.MEDIUM);
        guacamole.setDirections("Making Guacamole is easy\n" +
                "\n" +
                "All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help to balance the richness of the avocado. Then if you want, add chopped cilantro, chiles, onion, and/or tomato.\n" +
                "\n" +
                "Once you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. You can get creative with homemade guacamole!\n" +
                "Guacamole Tip: Use Ripe Avocados\n" +
                "\n" +
                "The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n" +
                "\n" +
                "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.");


        guacamole.setNotes(new Notes("Guacamole! Did you know that over 2 billion pounds of avocados are consumed each year in the U.S.? (Google it.) That’s over 7 pounds per person. I’m guessing that most of those avocados go into what has become America’s favorite dip, guacamole.\r\n" + 
        		"Where Does Guacamole Come From?\r\n" + 
        		"\r\n" + 
        		"The word “guacamole”, and the dip, are both originally from Mexico, where avocados have been cultivated for thousands of years. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce)."));
        // ingredients
        // create, set description, set amount
        // find unitofmeasure in the database and attach it to ingredient

        Ingredient ripeAvocado = new Ingredient();
        ripeAvocado.setAmount(new BigDecimal(2));
        ripeAvocado.setDescription("ripe avocados");

        Ingredient kosherSalt = new Ingredient();
        kosherSalt.setAmount(new BigDecimal(0.5));
        kosherSalt.setDescription("Kosher salt");


        Ingredient limeJuice = new Ingredient();
        limeJuice.setAmount(new BigDecimal(0.5));
        limeJuice.setDescription("Lime Juice");

        Ingredient redOnion = new Ingredient();
        redOnion.setAmount(new BigDecimal(0.5));
        redOnion.setDescription("Red onion");

        Ingredient blackPepper = new Ingredient();
        blackPepper.setAmount(new BigDecimal(0.5));
        blackPepper.setDescription("Black Pepper");

        ripeAvocado.setUom(teaspoon.get());
        kosherSalt.setUom(teaspoon.get());
        limeJuice.setUom(tablespoon.get());
        redOnion.setUom(tablespoon.get());
        blackPepper.setUom(dash.get());

        guacamole.addIngredient(ripeAvocado);
        guacamole.addIngredient(kosherSalt);
        guacamole.addIngredient(limeJuice);
        guacamole.addIngredient(redOnion);
        guacamole.addIngredient(blackPepper);
        
        //setting categories
        guacamole.getCategories().add(american.get());
        guacamole.getCategories().add(mexican.get());

        //preparing the spicy grilled chicken tacos

        Recipe grilledct = new Recipe("Spicy Grilled Chicken Tacos Recipe",20,15,6,"Simply Recipes","https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/", Difficulty.EASY);

        Ingredient ancho = new Ingredient("ancho chili powder",new BigDecimal(2),tablespoon.get());
        Ingredient oregano = new Ingredient("dried oregano",new BigDecimal(1),teaspoon.get());
        Ingredient cumin = new Ingredient("dried cumin",new BigDecimal(1),tablespoon.get());

        grilledct.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\r\n" + 
        		"\r\n" + 
        		"2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\r\n" + 
        		"\r\n" + 
        		"Set aside to marinate while the grill heats and you prepare the rest of the toppings.");
        
        grilledct.setNotes(new Notes("The ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.\r\n" + 
        		"\r\n" + 
        		"I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\r\n" + 
        		"\r\n" + 
        		"Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\r\n" + 
        		"\r\n" + 
        		"You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!"));
        
        grilledct.addIngredient(ancho);
        grilledct.addIngredient(oregano);
        grilledct.addIngredient(cumin);
        grilledct.getCategories().add(american.get());
        List<Recipe> resultList = new ArrayList<>();
        resultList.add(guacamole);
        resultList.add(grilledct);

        return resultList;

    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipeList());
    }
}
