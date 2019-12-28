package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.PAGE_NO;
import static com.eki.common.util.QueryConstants.QUESTION_MARK;
import static com.eki.common.util.QueryConstants.SLASH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.eki.common.interfaces.IEntity;
import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.model.Country;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.util.EntityFactory;

public class GeoScopeControllerTest extends AbstractWebControllerTest<GeoScope> {

	@Autowired()
	private TestRestTemplate restTemplate;
    
	@Test
	public void realWebEnvironmentTestFindOne() throws Exception {
		ResponseEntity<GeoScope> xy;
		GeoScope entity = retrieveFirstEntity(SLASH);
		ResponseEntity<GeoScope> responseEntity = restTemplate.exchange(createURL(SLASH + entity.getId()),
				HttpMethod.GET, defaultHttpEntity, GeoScope.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));

		GeoScope GeoScope = responseEntity.getBody();
		assertNotNull(GeoScope);
		assertThat(GeoScope.getId(), is(entity.getId()));

	}

	@Test
	public void realWebEnvironmentTestFindAll() throws Exception {

		ResponseEntity<List<GeoScope>> responseEntity = restTemplate.exchange(createURL(SLASH), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<GeoScope>>() {
				});
		assertFalse(responseEntity.getBody().isEmpty());
	}

	@Test
	public void realWebEnvironmentTestPost() {
		GeoScope entity = createEntity();
		HttpEntity<GeoScope> request = new HttpEntity<>(entity, headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(createURL(SLASH), request, String.class);
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = result.getHeaders();
		List<String> location = headers.get(HttpHeaders.LOCATION);
		assertNotNull(location);
		ResponseEntity<GeoScope> responseEntity = restTemplate.exchange(location.get(0), HttpMethod.GET,
				defaultHttpEntity, GeoScope.class);
		GeoScope retrievedGeoScope = responseEntity.getBody();

		assertEquals(entity.getName(), retrievedGeoScope.getName());

	}

	@Test
	public void realWebEnvironmentTestPutWithExchange() {
		GeoScope entity = retrieveFirstEntity(SLASH);
		entity.setName("");

		HttpEntity<GeoScope> request = new HttpEntity<>(entity, headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		ResponseEntity<GeoScope> responseEntity = restTemplate.exchange(createURL(SLASH + entity.getId()),
				HttpMethod.PUT, request, GeoScope.class, params);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

	}

	@Test
	public void realWebEnvironmentTestDeleteWithExchange() {
		GeoScope GeoScope = retrieveFirstEntity(SLASH);

		HttpEntity<GeoScope> request = new HttpEntity<>(null, headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, "8");
		ResponseEntity<String> responseEntity = restTemplate.exchange(createURL(SLASH + GeoScope.getId().toString()),
				HttpMethod.DELETE, request, String.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}

	@Test
	public void realWebEnvironmentTestFindAllPaged() throws Exception {

		ResponseEntity<List<GeoScope>> response = restTemplate.exchange(createURL(QUESTION_MARK + PAGE_NO + "=1"),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<GeoScope>>() {
				});
		assertFalse(response.getBody().isEmpty());

	}

	@Test
	public void realWebEnvironmentTestFindAllSortedParameterized() throws Exception {

		ResponseEntity<List<GeoScope>> responseEntity = restTemplate.exchange(
				createURL(QUESTION_MARK + " sortBy=code&sortOrder=DESC"), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<GeoScope>>() {
				});
		assertFalse(responseEntity.getBody().isEmpty());
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
		GeoScope[] body = this.restTemplate
				.getForObject("/geoscope/preferredPort?location_code=DUS&geo_scope_type=T&country_code=DE", GeoScope[].class);
		assertThat(body.length, is(equalTo(4)));

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

	protected GeoScope retrieveFirstEntity(String uriString) {

		ResponseEntity<List<GeoScope>> responseEntityAll = restTemplate.exchange(createURL(uriString), HttpMethod.GET,
				null, new ParameterizedTypeReference<List<GeoScope>>() {
				});
		assertFalse(responseEntityAll.getBody().isEmpty());
		return responseEntityAll.getBody().get(0);

	}

	@Override
	protected GeoScope createEntity() {
		return EntityFactory.createGeoScope();
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
	public boolean isSorted(List<GeoScope> resources, Comparator<GeoScope> comparator) {
		   if (resources.isEmpty() || resources.size() == 1) {
		        return true;
		    }
		 
		    Iterator<GeoScope> iter = resources.iterator();
		    GeoScope current, previous = iter.next();
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
	protected String getApiName() {
		return ShipmentMappings.GEOSCOPE;
	}
}
