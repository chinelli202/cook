package guru.springframework.recipe.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //weird acions

    private String description;

    public String getDescription() {
        return description;
    }

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();

    public Long getId() {
    	// freak
    	//and this is a comment
        return id;
    }
    
    //adding a new commit soon

    public void setId(Long id) {
        this.id = id;
    }
    //tryna commit these niggas

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

}
