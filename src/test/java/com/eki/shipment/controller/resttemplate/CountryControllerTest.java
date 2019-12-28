package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.PAGE_NO;
import static com.eki.common.util.QueryConstants.QUESTION_MARK;
import static com.eki.common.util.QueryConstants.SLASH;
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

public class CountryControllerTest extends AbstractWebControllerTest<Country> {

	@LocalServerPort
	private int port;



	@Test
	public void whenFindOne_ThenResourceIsRetrieved() {
		Country entity = retrieveFirstEntity(SLASH);
		ResponseEntity<Country> responseEntity = getOne(entity, Country.class, createURL(SLASH + entity.getId()));
		
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));
		Country country = responseEntity.getBody();
		assertNotNull(country);
		assertThat(country.getId(), is(entity.getId()));

	}

	@Test
	public void whenFindAll_thenResourcesAreRetrieved() throws Exception {

		ResponseEntity<List<Country>> responseEntity = 	 restTemplate.exchange(createURL(SLASH), HttpMethod.GET, null,new ParameterizedTypeReference<List<Country>>() {});
		assertFalse(responseEntity.getBody().isEmpty());
	}
	@Test
	public void findAllGeneric() throws Exception {
	
		ParameterizedTypeReference<List<Country>> parameterizedTypeReference = new ParameterizedTypeReference<List<Country>>() {};
		ResponseEntity<List<Country>> responseEntity =  getAll(createURL(SLASH),parameterizedTypeReference);
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
		ResponseEntity<Country> result = put(entity, Country.class, createURL(SLASH + entity.getId()), params);

		ResponseEntity<Country> responseEntity = restTemplate.exchange(createURL(SLASH + entity.getId()),
				HttpMethod.PUT, request, Country.class, params);
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
	public void realWebEnvironmentTestFindAllPaged() throws Exception {
		ResponseEntity<List<Country>> responseEntity = 	 restTemplate.exchange(createURL(QUESTION_MARK+PAGE_NO+"=1"), HttpMethod.GET, null,new ParameterizedTypeReference<List<Country>>() {});
		
		List<Country> resources= responseEntity.getBody();
		assertThat(resources.size(), is(5));

	}

	@Test
	public void whenFindAllSorted_thenResourceIsSorted() throws Exception {
		ResponseEntity<List<Country>> responseEntity = 	 restTemplate.exchange(createURL(QUESTION_MARK+"sortBy=code&sortOrder=DESC"), HttpMethod.GET, null,new ParameterizedTypeReference<List<Country>>() {});
			
		List<Country> resources= responseEntity.getBody();
		Comparator<Country> comparator = (o1, o2)->o1.getCode().compareTo(o2.getCode());
		assertThat(isSorted(resources, comparator), is(true));
		
	}

	@Test
	public void singleCountryTest() {
		Country country = retrieveFirstEntity(SLASH);
		Country body = this.restTemplate.getForObject("/country/find?country_code=" + country.getCode(), Country.class);
		assertThat(body.getName(), is(country.getName()));
	}

	@Test
	public void realWebEnvironmentTestFind() throws Exception {
		Country country = retrieveFirstEntity(SLASH);
		ResponseEntity<Country> response = restTemplate.exchange(createURL("/find?country_code=" + country.getCode()),
				HttpMethod.GET, defaultHttpEntity, Country.class);

		assertEquals(country, response.getBody());
	}

	protected Country retrieveFirstEntity(String uriString) {
		ResponseEntity<List<Country>> responseEntity = 	 restTemplate.exchange(createURL(uriString), HttpMethod.GET, null,new ParameterizedTypeReference<List<Country>>() {});
		assertFalse(responseEntity.getBody().isEmpty());
		return responseEntity.getBody().get(0);

	}
	@Override
	public  boolean isSorted(List<Country> resources, Comparator<Country> comparator) {
	    if (resources.isEmpty() || resources.size() == 1) {
	        return true;
	    }
	 
	    Iterator<Country> iter = resources.iterator();
	    Country current, previous = iter.next();
	    while (iter.hasNext()) {
	        current = iter.next();
	        if (comparator.compare(previous, current) < 0) {
	            return false;
	        }
	        previous = current;
	    }
	    return true;
	}

	@Override
	protected String createURL(String uriPart) {
		StringBuilder uri = createBaseUri(uriPart);
		uri.append(getApiName());
		if (!uriPart.isEmpty()) {
			uri.append(uriPart);
		}
		return uri.toString();
	}

	@Override
	protected Country createEntity() {
		return EntityFactory.createNewCountry();
	}

	@Override
	protected String getApiName() {
		return ShipmentMappings.COUNTRY;
	}

}
