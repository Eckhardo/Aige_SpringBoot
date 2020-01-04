package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.SLASH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		Country entity = createEntity();
		ResponseEntity<Country> result = post(entity, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = result.getHeaders();
		List<String> location = headers.get(HttpHeaders.LOCATION);
		assertNotNull(location);
		ResponseEntity<Country> responseEntity = restTemplate.exchange(location.get(0), HttpMethod.GET,
				defaultHttpEntity, Country.class);
		Country retrievedCountry = responseEntity.getBody();

		assertEquals(entity.getName(), retrievedCountry.getName());

	}

	@Test
	public void whenUpdateResource_thenStatusCodeIsOk() {
		Country entity = createNewEntityAndPersist();
		entity.setName("");

		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		ResponseEntity<Country> responseEntity = put(entity, createURL(SLASH + entity.getId()), params);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

	}

	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		Country entity = createNewEntityAndPersist();

		ResponseEntity<Country> responseEntity = delete(entity, createURL(SLASH + entity.getId().toString()));

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}

	@Test
	public void whenFindByCode_thenResourcesListIsReturned() throws Exception {
		Country country = createNewEntityAndPersist();
		ResponseEntity<Country> response = restTemplate.exchange(createURL("/find?country_code=" + country.getCode()),
				HttpMethod.GET, defaultHttpEntity, Country.class);

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
	protected ParameterizedTypeReference<List<Country>> getResponseType() {
		return new ParameterizedTypeReference<List<Country>>() {
		};
	}

	@Override
	protected TypeReference<List<Country>> getTypeRef() {
		return new TypeReference<List<Country>>() {
		};
	}

}
