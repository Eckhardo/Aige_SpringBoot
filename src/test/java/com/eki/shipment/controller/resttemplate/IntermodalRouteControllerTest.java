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
import com.eki.shipment.model.KeyFigure;
import com.fasterxml.jackson.core.type.TypeReference;

import data.IntermodalRouteFigureData;

public class IntermodalRouteControllerTest extends AbstractWebControllerTest<KeyFigure> {

	public IntermodalRouteControllerTest() {
		super(KeyFigure.class);
	}

	@Test
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		KeyFigure entity = createEntity();
		ResponseEntity<KeyFigure> result = post(entity, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = result.getHeaders();
		List<String> location = headers.get(HttpHeaders.LOCATION);
		assertNotNull(location);
		ResponseEntity<KeyFigure> responseEntity = restTemplate.exchange(location.get(0), HttpMethod.GET,
				defaultHttpEntity, KeyFigure.class);
		KeyFigure retrievedReosurce = responseEntity.getBody();

		assertEquals(entity.getRate(), retrievedReosurce.getRate());

	}

	@Test
	public void whenUpdateResource_thenStatusCodeIsOk() {
		KeyFigure entity = createNewEntityAndPersist();
		entity.setTransportMode("");

		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		ResponseEntity<KeyFigure> result = put(entity, createURL(SLASH + entity.getId()), params);

		assertThat(result.getStatusCode(), is(HttpStatus.OK));

	}

	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		KeyFigure entity = createNewEntityAndPersist();

		ResponseEntity<KeyFigure> responseEntity = delete(entity, createURL(SLASH + entity.getId().toString()));

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}

	@Override
	protected String getApiName() {
		return ShipmentMappings.INTERMODAL_ROUTE;
	}

	@Override
	protected KeyFigure createEntity() {
		return IntermodalRouteFigureData.getSingle();
	}

	@Override
	protected ParameterizedTypeReference<List<KeyFigure>> getResponseType() {

		return new ParameterizedTypeReference<List<KeyFigure>>() {
		};
	}

	@Override
	protected TypeReference<List<KeyFigure>> getTypeRef() {
		return new TypeReference<List<KeyFigure>>() {
		};
	}

}
