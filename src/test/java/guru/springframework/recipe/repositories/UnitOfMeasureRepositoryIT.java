package guru.springframework.recipe.repositories;

import guru.springframework.recipe.domain.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void findByDescription() {
        UnitOfMeasure unit = unitOfMeasureRepository.findByDescription("Teaspoon").get();

        assertEquals("Teaspoon",unit.getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        UnitOfMeasure unit = unitOfMeasureRepository.findByDescription("Cup").get();

        assertEquals("Cup",unit.getDescription());
    }
}