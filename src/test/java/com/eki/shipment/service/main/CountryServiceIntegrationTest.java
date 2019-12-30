package com.eki.shipment.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.eki.shipment.model.Country;
import com.eki.shipment.service.CountryService;


public class CountryServiceIntegrationTest extends AbstractServiceIntegrationTest<Country> {

	@Autowired
	CountryService serviceUnderTest;

	@Test
	public void whenSaveIsPerformed_thenNoException() {
		getApi().create(createNewEntity());
	}

	@Test(expected = DataAccessException.class)
	public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
		final String name = randomAlphabetic(3);

		getApi().create(createNewEntity(name));
		getApi().create(createNewEntity(name));
	}

	@Override
	protected void invalidate(Country invalidResource) {
		invalidResource.setCode(randomAlphabetic(30));

	}

	@Override
	protected void change(Country resource) {
		resource.setCode("");

	}

	@Override
	protected Country createNewEntity() {
		return new Country(randomAlphabetic(6), randomAlphabetic(2));

	}

	protected Country createNewEntity(String name) {
		return new Country(name, randomAlphabetic(3));

	}

	@Override
	protected CountryService getApi() {
		return serviceUnderTest;
	}

}
