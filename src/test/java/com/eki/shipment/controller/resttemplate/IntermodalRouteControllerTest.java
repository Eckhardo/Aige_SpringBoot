package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.COMPLETE_PAGE_REQUEST;
import static com.eki.common.util.QueryConstants.COMPLETE_SORT_ORDER;
import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.PAGE_NO;
import static com.eki.common.util.QueryConstants.QUESTION_MARK;
import static com.eki.common.util.QueryConstants.SLASH;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Comparator;
import java.util.HashMap;
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
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.model.OceanRoute;
import com.eki.shipment.model.Country;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.service.GeoScopeService;
import com.eki.shipment.service.IntermodalRouteService;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;

import data.IntermodalRouteFigureData;

public class IntermodalRouteControllerTest extends AbstractWebControllerTest<KeyFigure>{

	
	
	public IntermodalRouteControllerTest() {
		super(KeyFigure.class);
	}
	
	



	@Test
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		KeyFigure entity = createEntity();
		ResponseEntity<KeyFigure> result = post(entity, KeyFigure.class, createURL(SLASH));
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
		ResponseEntity<KeyFigure> result = put(entity, KeyFigure.class, createURL(SLASH + entity.getId()), params);

	
		assertThat(result.getStatusCode(), is(HttpStatus.OK));

	}



	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		KeyFigure entity = createNewEntityAndPersist();

	    ResponseEntity<KeyFigure> responseEntity = delete(entity, KeyFigure.class,
				createURL(SLASH + entity.getId().toString()));

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}
	@Test
	public void whenFindAllPaged_thenResourcesArePaged() throws Exception {

		ResponseEntity<List<KeyFigure>> response = restTemplate.exchange(createURL(COMPLETE_PAGE_REQUEST),
				HttpMethod.GET, null, getParamTypeRef());
		assertFalse(response.getBody().isEmpty());

	}
	@Test
	public void  whenFindAllPaged_thenResourceIsPaged() throws Exception {
		ResponseEntity<List<KeyFigure>> responseEntity = 	 restTemplate.exchange(createURL(COMPLETE_PAGE_REQUEST), HttpMethod.GET, null,getParamTypeRef());
		
		List<KeyFigure> resources= responseEntity.getBody();
		assertThat(resources.size(), is(5));

	}

	@Test
	public void whenFindAllSorted_thenResourceIsSorted_2() throws Exception {

		ResponseEntity<List<KeyFigure>> responseEntity = restTemplate.exchange(
				createURL(COMPLETE_SORT_ORDER), HttpMethod.GET, null,
				getParamTypeRef());
		assertFalse(responseEntity.getBody().isEmpty());
	}



	@Test
	public void whenFindAllSorted_thenResourceIsSorted() throws Exception {
		ResponseEntity<List<KeyFigure>> responseEntity = 	 restTemplate.exchange(createURL(COMPLETE_SORT_ORDER), HttpMethod.GET, null,getParamTypeRef());
			
		List<KeyFigure> resources= responseEntity.getBody();
		Comparator<KeyFigure> comparator = (o1, o2)->o1.getId().compareTo(o2.getId());
		assertThat(isSorted(resources, comparator), is(true));
		
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
	protected ParameterizedTypeReference<List<KeyFigure>> getParamTypeRef() {
		
		return new ParameterizedTypeReference<List<KeyFigure>>() {
		};
	}

	@Override
	protected TypeReference<List<KeyFigure>> getTypeRef() {
	return new TypeReference<List<KeyFigure>>() {
	};
	}


	
	

	

}
