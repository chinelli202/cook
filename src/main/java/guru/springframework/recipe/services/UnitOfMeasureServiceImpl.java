package guru.springframework.recipe.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

	UnitOfMeasureRepository unitOfMeasureRepository;
	
	
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}



	
	@Override
	public Set<UnitOfMeasure> listAllUoms() {
		// TODO Auto-generated method stub
		return unitOfMeasureRepository.findAll();
	}

}
