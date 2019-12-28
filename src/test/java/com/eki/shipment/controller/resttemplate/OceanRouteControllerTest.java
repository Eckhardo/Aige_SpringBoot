package com.eki.shipment.controller.resttemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.eki.shipment.model.OceanRoute;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OceanRouteControllerTest extends AbstractWebControllerTest<OceanRoute> {
	Logger logger = LoggerFactory.getLogger(OceanRouteControllerTest.class);

	@LocalServerPort
	private int port;

	HttpHeaders headers = new HttpHeaders();

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper jsonObjectMapper;

	@Test
	public void getValidRoutes() {
		OceanRoute[] body = this.restTemplate.getForObject(
				"/oceanroute/filter?includeInvalid=false&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG",
				OceanRoute[].class);
		assertThat(body.length, is(not(0)));
		for (int i = 0; i < body.length; i++) {
			OceanRoute kf = body[i];
		}
	}

	@Test
	public void getAllRoutes() {
		OceanRoute[] body = this.restTemplate.getForObject(
				"/oceanroute/filter?includeInvalid=true&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG",
				OceanRoute[].class);
		assertThat(body.length, is(not(0)));
		for (int i = 0; i < body.length; i++) {
			OceanRoute kf = body[i];
		}
	}

	@Test
	public void realWebEnvironmentTest() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(
				"/nre/oceanroute/filter?includeInvalid=true&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG&ts1=COCTG"),
				HttpMethod.GET, entity, String.class);
		OceanRoute[] route = jsonObjectMapper.readValue(response.getBody(), OceanRoute[].class);

		assertEquals(route[0].getPol(), "BRSSZ");

	}

	@Test
	public void realWebEnvironmentTestWithTypeRefefernce() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(
				"/nre/oceanroute/filter?includeInvalid=true&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG&ts1=COCTG"),
				HttpMethod.GET, entity, String.class);
		TypeReference<List<OceanRoute>> typeRef = new TypeReference<List<OceanRoute>>() {
		};

		List<OceanRoute> route = jsonObjectMapper.readValue(response.getBody(), typeRef);

		assertEquals(route.get(0).getPol(), "BRSSZ");

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Override
	protected String getApiName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected OceanRoute createEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createURL(String uriPart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSorted(List<OceanRoute> resources, Comparator<OceanRoute> comparator) {
		// TODO Auto-generated method stub
		return false;
	}


}