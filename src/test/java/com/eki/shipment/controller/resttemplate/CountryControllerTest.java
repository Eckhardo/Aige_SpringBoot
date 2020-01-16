package com.eki.shipment.controller.resttemplate;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class CountryControllerTest extends AbstractWebControllerTest<Country> {

	public CountryControllerTest() {
		super(Country.class);
	}





	@Test
	public void whenFindByCode_thenResourcesListIsReturned() throws Exception {
		Country country = createNewEntityAndPersist();
		ResponseEntity<Country> response = restTemplate.exchange(createURL("/find?country_code=" + country.getCode()),
				HttpMethod.GET, getHttpEntity(country), Country.class);

		assertEquals(country, response.getBody());
	}

	@Override
	protected Country createEntity() {
		return EntityFactory.createNewCountry();
	}

	@Override
	protected String getApiName() {
		return ShipmentMappings.COUNTRY;
	}



	@Override
	protected TypeReference<List<Country>> getTypeRef() {
		return new TypeReference<List<Country>>() {
		};
	}

}
