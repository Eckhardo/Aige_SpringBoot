package com.eki.shipment.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.eki.shipment.model.Country;

public class CountryRepositoryTest extends AbstractRepositoryTest{

		
	@Autowired
	private CountryRepository countryRepository;
	
	
	@Test
	public void whenFindByName_thenReturnCountry() {
	    // given
	    Country absurdistan = new Country(100,"Serbia","SB");
	  absurdistan =  countryRepository.save(absurdistan);
	    

	 
	    // when
	    Country found = countryRepository.findByCode(absurdistan.getCode());
	 
	    // then
	    assertThat(found.getName())
	      .isEqualTo(absurdistan.getName());
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
}
