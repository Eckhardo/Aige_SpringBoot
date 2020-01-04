package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.SLASH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
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
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class GeoScopeControllerTest extends AbstractWebControllerTest<GeoScope> {

	public GeoScopeControllerTest() {
		super(GeoScope.class);
	}

	@Test
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		GeoScope entity = createEntity();
		ResponseEntity<GeoScope> result = post(entity, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = result.getHeaders();
		List<String> location = headers.get(HttpHeaders.LOCATION);
		assertNotNull(location);
		ResponseEntity<GeoScope> responseEntity = restTemplate.exchange(location.get(0), HttpMethod.GET,
				defaultHttpEntity, GeoScope.class);

		assertEquals(entity.getName(), responseEntity.getBody().getName());

	}

	@Test
	public void whenUpdateResource_thenStatusCodeIsOk() {
		GeoScope entity = createNewEntityAndPersist();
		entity.setName("");

		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		ResponseEntity<GeoScope> result = put(entity, createURL(SLASH + entity.getId()), params);

		assertThat(result.getStatusCode(), is(HttpStatus.OK));

	}

	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		GeoScope entity = createNewEntityAndPersist();

		ResponseEntity<GeoScope> responseEntity = delete(entity, createURL(SLASH + entity.getId().toString()));

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}

	@Test
	public void whenSearchGeoScopesLikeDED_thenReturnLocations() {
		GeoScope[] body = this.restTemplate
				.getForObject("/geoscope/filter?location_code=DED&geo_scope_type=L&country_code=DE", GeoScope[].class);
		assertThat(body.length, is(3));

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].getLocationCode(), startsWith("DED"));
		}
	}

	@Test
	public void whenSearchGeoScopesLikeDUS_thenReturnCitys() {
		GeoScope[] body = this.restTemplate.getForObject("/geoscope/filter?location_code=DUS&geo_scope_type=T",
				GeoScope[].class);
		assertThat(body.length, is(1));

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].getLocationCode(), equalTo("DUSSELDORF"));
		}
	}

	@Test
	public void whenSearchPreferredPorts_thenReturnPorts() {
		GeoScope[] body = this.restTemplate.getForObject(
				"/geoscope/preferredPort?location_code=DUS&geo_scope_type=T&country_code=DE", GeoScope[].class);

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].isPort(), is(true));
		}
	}

	@Test
	public void whenSearchPorts_thenReturnPorts() {
		GeoScope[] body = this.restTemplate.getForObject("/geoscope/ports?location_code=ARB", GeoScope[].class);
		assertThat(body.length, is(equalTo(1)));

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].isPort(), is(true));
		}
	}

	@Override
	protected GeoScope createEntity() {
		return EntityFactory.createGeoScope();
	}

	@Override
	protected String getApiName() {
		return ShipmentMappings.GEOSCOPE;
	}

	@Override
	protected ParameterizedTypeReference<List<GeoScope>> getResponseType() {

		return new ParameterizedTypeReference<List<GeoScope>>() {
		};
	}

	@Override
	protected TypeReference<List<GeoScope>> getTypeRef() {

		return new TypeReference<List<GeoScope>>() {
		};
	}
}
