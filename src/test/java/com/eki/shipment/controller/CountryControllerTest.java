package com.eki.shipment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.eki.shipment.model.Country;

public class CountryControllerTest extends AbstractWebControllerTest {

	@LocalServerPort
	private int port;

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
		assertThat(body.length, is(2));
	}

	@Test
	public void realWebEnvironmentTestFindOne() throws Exception {

		ResponseEntity<String> response = restTemplate.exchange(createURL("/1"), HttpMethod.GET, requestEntity,
				String.class);

		String expected = "{\"id\":1,\"name\":\"Germany\",\"code\":\"DE\"}";
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void realWebEnvironmentTestFind() throws Exception {

		ResponseEntity<String> response = restTemplate.exchange(createURL("/find?country_code=DE"), HttpMethod.GET,
				requestEntity, String.class);

		String expected = "{\"id\":1,\"name\":\"Germany\",\"code\":\"DE\"}";
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void realWebEnvironmentTestFindAllPaged() throws Exception {

		ResponseEntity<List> response = restTemplate.exchange(createURL("/paged?pageNo=1&pageSize=5"), HttpMethod.GET,
				requestEntity, List.class);
		assertThat(response.getBody().size(), is(5));

	}

	@Test
	public void realWebEnvironmentTestFindAllSortedParameterized() throws Exception {

		ResponseEntity<List<Country>> responseEntity = restTemplate.exchange(createURL("/sorted?sortBy=code"),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Country>>() {
				});
		assertThat(responseEntity.getBody().size(), is(16));
	}

	private String createURL(String uriPart) {
		return "http://localhost:" + port + "/nre/country" + uriPart;
	}
}
