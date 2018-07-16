package com.eki.country;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.Country;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CountryRepositoryTest {

	
	@Autowired
	
	private TestEntityManager entityManager;
	
	@Autowired
	private CountryRepository countryRepository;
	
	
	@Test
	public void whenFindByName_thenReturnCountry() {
	    // given
	    Country absurdistan = new Country("Absurdistan","YX");
	    entityManager.persist(absurdistan);
	    entityManager.flush();
	 
	    // when
	    Optional<Country> found = countryRepository.findByCode(absurdistan.getCode());
	 
	    // then
	    assertThat(found.get().getName())
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
