package com.eki.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.Country;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CountryControllerTest {

	@LocalServerPort
	private int port;

	HttpHeaders headers = new HttpHeaders();

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
	public void realWebEnvironmentTest() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/nre/country/find?country_code=DE"),
				HttpMethod.GET, entity, String.class);

		String expected = "{\"id\":1,\"name\":\"Germany\",\"code\":\"DE\"}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
