package com.eki.shipment.controller.resttemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.model.OceanRoute;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OceanRouteControllerTest extends AbstractWebControllerTest<OceanRoute> {
	Logger logger = LoggerFactory.getLogger(OceanRouteControllerTest.class);

	@Autowired
	private ObjectMapper jsonObjectMapper;

	public OceanRouteControllerTest() {
		super(OceanRoute.class);
	}



	@Test
	public void whenSearchForValidRoutes_onlyValidResourcesAreReturned() {
		OceanRoute[] body = this.restTemplate.getForObject(
				"/oceanroute/filter?includeInvalid=false&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG",
				OceanRoute[].class);
		assertThat(body.length, is(not(0)));
		for (int i = 0; i < body.length; i++) {
			OceanRoute kf = body[i];
		}
	}

	@Test
	public void whenSearchForRoutesGetForObject_onlyMatchingResourcesAreReturned() {
		OceanRoute[] body = this.restTemplate.getForObject(
				"/oceanroute/filter?includeInvalid=true&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG",
				OceanRoute[].class);
		assertThat(body.length, is(not(0)));
		for (int i = 0; i < body.length; i++) {
			OceanRoute kf = body[i];
		}
	}

	@Test
	public void whenSearchRoutesExchange_onlyMatchingResourcesAreReturned() throws Exception {

	
		ResponseEntity<String> response = restTemplate.exchange(
				createURL("/filter?includeInvalid=true&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG&ts1=COCTG"),
				HttpMethod.GET,  getHttpEntity(null), String.class);
		TypeReference<List<OceanRoute>> typeRef = getTypeRef();

		List<OceanRoute> routes = jsonObjectMapper.readValue(response.getBody(), typeRef);

		assertNotNull(routes);

	}

	@Test
	public void whenSearchForRoutesWithTypeRef_matchingResourcesAreReturned() throws Exception {

		ResponseEntity<String> response = restTemplate.exchange(
				createURL("/filter?includeInvalid=true&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG&ts1=COCTG"),
				HttpMethod.GET,  getHttpEntity(null), String.class);

		List<OceanRoute> route = jsonObjectMapper.readValue(response.getBody(), getTypeRef());

		assertEquals(route.get(0).getPol(), "BRSSZ");

	}



	@Override
	protected OceanRoute createEntity() {
		return EntityFactory.createOceanRoute();
	}

	@Override
	protected String getApiName() {
		return ShipmentMappings.OCEAN_ROUTE;
	}
	@Override
	protected TypeReference<List<OceanRoute>> getTypeRef() {

		return new TypeReference<List<OceanRoute>>() {
		};
	}


}