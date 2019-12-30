package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class CountryControllerTest extends AbstractWebControllerTest<Country> {






	@Test
	public void whenFindAll_thenResourcesAreRetrieved() throws Exception {

		ResponseEntity<List<Country>> responseEntity = 	 restTemplate.exchange(createURL(SLASH), HttpMethod.GET, null,getParamTypeRef());
		assertFalse(responseEntity.getBody().isEmpty());
	}
	@Test
	public void whenFindAllWithTypeRef_thenResourcesAreRetrieved() throws Exception {
	
		ResponseEntity<List<Country>> responseEntity =  getAll(createURL(SLASH),getParamTypeRef());
		assertFalse(responseEntity.getBody().isEmpty());
	}



	@Test
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		Country entity = createEntity();
		ResponseEntity<Country> result = post(entity, Country.class, createURL(SLASH));
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
		Country entity = retrieveFirstEntity(SLASH);
		entity.setName("");

		HttpEntity<Country> request = new HttpEntity<>(entity, headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		ResponseEntity<Country> responseEntity = put(entity, Country.class, createURL(SLASH + entity.getId()), params);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

	}



	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		Country entity = retrieveFirstEntity(SLASH);

	    ResponseEntity<Country> responseEntity = delete(entity, Country.class,
				createURL(SLASH + entity.getId().toString()));

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}
	@Test
	public void whenFindAllPaged_thenResourcesArePaged() throws Exception {

		ResponseEntity<List<Country>> response = restTemplate.exchange(createURL(QUESTION_MARK + PAGE_NO + "=1"),
				HttpMethod.GET, null,getParamTypeRef());
		assertFalse(response.getBody().isEmpty());

	}

	@Test
	public void whenFindAllSorted_thenResourceIsSorted_2() throws Exception {

		ResponseEntity<List<Country>> responseEntity = restTemplate.exchange(
				createURL(COMPLETE_SORT_ORDER), HttpMethod.GET, null,getParamTypeRef());
		assertFalse(responseEntity.getBody().isEmpty());
	}

	@Test
	public void  whenFindAllPaged_thenResourceIsPaged() throws Exception {
		ResponseEntity<List<Country>> responseEntity = 	 restTemplate.exchange(createURL(COMPLETE_PAGE_REQUEST), HttpMethod.GET, null,getParamTypeRef());
		
		List<Country> resources= responseEntity.getBody();
		assertThat(resources.size(), is(5));

	}

	@Test
	public void whenFindAllSorted_thenResourceIsSorted() throws Exception {
		ResponseEntity<List<Country>> responseEntity = 	 restTemplate.exchange(createURL(COMPLETE_SORT_ORDER), HttpMethod.GET, null,getParamTypeRef());
			
		List<Country> resources= responseEntity.getBody();
		Comparator<Country> comparator = (o1, o2)->o1.getId().compareTo(o2.getId());
		assertThat(isSorted(resources, comparator), is(true));
		
	}


	@Test
	public void whenFindByCode_thenResourcesListIsReturned() throws Exception {
		Country country = retrieveFirstEntity(SLASH);
		ResponseEntity<Country> response = restTemplate.exchange(createURL("/find?country_code=" + country.getCode()),
				HttpMethod.GET, defaultHttpEntity, Country.class);

		assertEquals(country, response.getBody());
	}

	protected Country retrieveFirstEntity(String uriString) {
		ResponseEntity<List<Country>> responseEntity = 	 restTemplate.exchange(createURL(uriString), HttpMethod.GET, null,getParamTypeRef());
		assertFalse(responseEntity.getBody().isEmpty());
		return responseEntity.getBody().get(0);

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
	protected ParameterizedTypeReference<List<Country>> getParamTypeRef() {
	return new ParameterizedTypeReference<List<Country>>() {
	};
	}
	@Override
	protected TypeReference<List<Country>> getTypeRef() {
		return new TypeReference<List<Country>>() {
		};
	}

}