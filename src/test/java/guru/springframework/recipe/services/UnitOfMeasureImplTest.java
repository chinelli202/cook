package guru.springframework.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class UnitOfMeasureImplTest {

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	UnitOfMeasureServiceImpl unitOfMeasureServiceImpl; 
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		unitOfMeasureServiceImpl = new UnitOfMeasureServiceImpl(unitOfMeasureRepository);
	}
	
	@Test
	public void testListAllUoms() {
		//given
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		uom1.setDescription("dash");
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		uom2.setDescription("ounce");
		
		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom3.setId(3L);
		uom3.setDescription("cup");
		
		Set<UnitOfMeasure> uomList = new HashSet<>(); 
		uomList.add(uom1);
		uomList.add(uom2);
		uomList.add(uom3);
		//when
		when(unitOfMeasureRepository.findAll()).thenReturn(uomList);
		Set<UnitOfMeasure> returnedSet = unitOfMeasureServiceImpl.listAllUoms();
		
		//then
		assertEquals(3, returnedSet.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}
}
