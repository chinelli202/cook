package guru.springframework.recipe.bootstrap;

import guru.springframework.recipe.domain.Difficulty;
import guru.springframework.recipe.domain.Ingredient;
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

        guacamole.setCookTime(0);
        guacamole.setPrepTime(600);
        guacamole.setServings(4);
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

        //preparing the spicy grilled chicken tacos

        Recipe grilledct = new Recipe("Spicy Grilled Chicken Tacos Recipe",20,15,6,"Simply Recipes","https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/", Difficulty.EASY);

        Ingredient ancho = new Ingredient("ancho chili powder",new BigDecimal(2),tablespoon.get());
        Ingredient oregano = new Ingredient("dried oregano",new BigDecimal(1),teaspoon.get());
        Ingredient cumin = new Ingredient("dried cumin",new BigDecimal(1),tablespoon.get());

        grilledct.addIngredient(ancho);
        grilledct.addIngredient(oregano);
        grilledct.addIngredient(cumin);

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
