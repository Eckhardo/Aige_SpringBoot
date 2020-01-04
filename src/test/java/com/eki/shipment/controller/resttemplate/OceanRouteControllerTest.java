package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.SLASH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		OceanRoute entity = createEntity();
		ResponseEntity<OceanRoute> result = post(entity, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = result.getHeaders();
		List<String> location = headers.get(HttpHeaders.LOCATION);
		assertNotNull(location);
		ResponseEntity<OceanRoute> responseEntity = restTemplate.exchange(location.get(0), HttpMethod.GET,
				defaultHttpEntity, OceanRoute.class);
		OceanRoute retrievedOceanRoute = responseEntity.getBody();

		assertEquals(entity.getPol(), retrievedOceanRoute.getPol());

	}

	@Test
	public void whenUpdateResource_thenStatusCodeIsOk() {
		OceanRoute entity = createNewEntityAndPersist();
		entity.setPol("");
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		ResponseEntity<OceanRoute> responseEntity = put(entity, createURL(SLASH + entity.getId()), params);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

	}

	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		OceanRoute entity = createNewEntityAndPersist();

		ResponseEntity<OceanRoute> responseEntity = delete(entity, createURL(SLASH + entity.getId().toString()));

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

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

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURL("/filter?includeInvalid=true&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG&ts1=COCTG"),
				HttpMethod.GET, entity, String.class);
		TypeReference<List<OceanRoute>> typeRef = getTypeRef();

		List<OceanRoute> routes = jsonObjectMapper.readValue(response.getBody(), typeRef);

		assertNotNull(routes);

	}

	@Test
	public void whenSearchForRoutesWithTypeRef_matchingResourcesAreReturned() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURL("/filter?includeInvalid=true&includeShunting=false&numberTs=1&pol=BRSSZ&pod=HKHKG&ts1=COCTG"),
				HttpMethod.GET, entity, String.class);

		List<OceanRoute> route = jsonObjectMapper.readValue(response.getBody(), getTypeRef());

		assertEquals(route.get(0).getPol(), "BRSSZ");

	}

	@Override
	protected TypeReference<List<OceanRoute>> getTypeRef() {
		return new TypeReference<List<OceanRoute>>() {
		};

	}

	@Override
	protected ParameterizedTypeReference<List<OceanRoute>> getResponseType() {
		return new ParameterizedTypeReference<List<OceanRoute>>() {
		};
	}

	@Override
	protected OceanRoute createEntity() {
		return EntityFactory.createOceanRoute();
	}

	@Override
	protected String getApiName() {
		return ShipmentMappings.OCEAN_ROUTE;
	}

}