package com.eki.shipment.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.eki.shipment.model.Country;
import com.eki.shipment.service.CountryService;


public class CountryServiceIntegrationTest extends AbstractServiceIntegrationTest<Country> {

	@Autowired
	CountryService serviceUnderTest;

	@Test
	public void whenSaveIsPerformed_thenNoException() {
		getApi().create(createNewEntity());
	}

	
	public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
		final String code = randomAlphabetic(3);

		getApi().create(createNewEntity(code));
		getApi().create(createNewEntity(code));
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

	protected Country createNewEntity(String code) {
		return new Country(randomAlphabetic(30),code);

	}

	@Override
	protected CountryService getApi() {
		return serviceUnderTest;
	}

}
