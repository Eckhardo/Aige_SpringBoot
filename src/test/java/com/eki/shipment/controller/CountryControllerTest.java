package com.eki.shipment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CountryControllerTest extends AbstractWebControllerTest {

	@LocalServerPort
	private int port;
	private static final ObjectMapper om = new ObjectMapper();

	HttpHeaders headers = new HttpHeaders();

	HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void singleCountryTest() {
		Country body = this.restTemplate.getForObject("/country/find?country_code=DE", Country.class);
		assertThat(body.getName(), is("Germany"));
	}

	@Test
	public void likeCountryTest() {
		Country[] body = this.restTemplate.getForObject("/country/filter?country_code=D", Country[].class);
		assertFalse(body.length == 0);
	}

	@Test
	public void realWebEnvironmentTestFindOne() throws Exception {

		ResponseEntity<Country> responseEntity = restTemplate.exchange(createURL("/1"), HttpMethod.GET, requestEntity,
				Country.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));

		Country country = responseEntity.getBody();
		assertNotNull(country);
		assertThat(country.getId(), is(1L));

	}

	@Test
	public void realWebEnvironmentTestFind() throws Exception {

		ResponseEntity<String> response = restTemplate.exchange(createURL("/find?country_code=DE"), HttpMethod.GET,
				requestEntity, String.class);

		String expected = "{\"id\":1,\"name\":\"Germany\",\"code\":\"DE\"}";
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void realWebEnvironmentTestPost() {
		Country country = EntityFactory.createNewCountry();
		HttpEntity<Country> request = new HttpEntity<>(country, headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(createURL("/"), request, String.class);
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
	}



	@Test
	public void realWebEnvironmentTestPutWithExchange() {
		Country country = EntityFactory.createNewCountry();
		country.setId(6L);
		HttpEntity<Country> request = new HttpEntity<>(country, headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "6");
		ResponseEntity<Country> responseEntity = restTemplate.exchange(createURL("/6"), HttpMethod.PUT, request,
				Country.class,params);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

	}


	@Test
	public void realWebEnvironmentTestDeleteWithExchange() {
	HttpEntity<Country> request = new HttpEntity<>(null, headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "8");
		ResponseEntity<String> responseEntity = restTemplate.exchange(createURL("/8"), HttpMethod.DELETE, request,
				String.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}
	@Test
	public void realWebEnvironmentTestFindAllPaged() throws Exception {

		ResponseEntity<List> response = restTemplate.exchange(createURL("/paged?pageNo=1&pageSize=5"), HttpMethod.GET,
				requestEntity, List.class);
		assertFalse(response.getBody().isEmpty());

	}

	@Test
	public void realWebEnvironmentTestFindAllSortedParameterized() throws Exception {

		ResponseEntity<List<Country>> responseEntity = restTemplate.exchange(createURL("/sorted?sortBy=code"),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Country>>() {
				});
		assertFalse(responseEntity.getBody().isEmpty());
	}

	private String createURL(String uriPart) {
		return "http://localhost:" + port + "/nre/country" + uriPart;
	}
}
