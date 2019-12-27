package com.eki.shipment.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eki.common.interfaces.IEntity;
import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;

public class CountryRepositoryTest extends AbstractRepositoryTest<Country,Long>{

		
	@Autowired
	private CountryRepository countryRepository;
	
	
	@Test
	public void whenFindByName_thenReturnCountry() {
	    // given
	
	Country  newCountry =  countryRepository.save(createNewEntity());
	    

	 
	    // when
	    Country found = countryRepository.findByName(newCountry.getName());
	 
	    // then
	    assertThat(found.getName()).isEqualTo(newCountry.getName());
	}
	
	@Test
	public void whenFindByCode_thenReturnCountryCollection() {
	    // given
	 
	    // when
	    Collection<Country> found = countryRepository.findByCodeLike("D");
	 
	    // then
	    assertThat(found.isEmpty(), is(false));
	    found.stream().allMatch((Country c) -> c.getCode().startsWith("D"));
	}
	@Override
	protected Country createNewEntity() {
		// TODO Auto-generated method stub
	return EntityFactory.createNewCountry();
	}

	@Override
	protected CountryRepository getApi() {
		return countryRepository;
	}


}
