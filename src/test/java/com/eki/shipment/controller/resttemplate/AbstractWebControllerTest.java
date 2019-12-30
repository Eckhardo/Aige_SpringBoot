package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.SLASH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.eki.common.interfaces.IDto;
import com.eki.common.interfaces.IEntity;
import com.eki.shipment.model.Country;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.model.OceanRoute;
import com.eki.shipment.run.MysqlBootApplication;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * TestRestTemplate is only auto-configured when @SpringBootTest has been
 * configured with a webEnvironment that means it starts the web container and
 * listens for HTTP requests.
 * 
 * @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT) @author eckha
 *
 */
@SpringBootTest(classes = MysqlBootApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")

public abstract class AbstractWebControllerTest<T extends IEntity> {
	
	
	Class<T> clazz;

	public AbstractWebControllerTest(Class clazz) {
		super();
		this.clazz=clazz;
	}
	@Autowired
	TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	final String HOST = "http://localhost:";

	final String APP_ROOT = "nre";

	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> defaultHttpEntity = new HttpEntity<String>(null, headers);

	

	@Test
	public void whenFindAllWithTypeRef_thenResourcesAreRetrieved() throws Exception {
	
		ResponseEntity<List<T>> responseEntity =  getAll(createURL(SLASH),getParamTypeRef());
		assertFalse(responseEntity.getBody().isEmpty());
	}

	@Test
	public void whenFindOne_ThenResourceIsRetrieved() {
		T entity =createNewEntityAndPersist();
		ResponseEntity<T> responseEntity = getOne(entity, clazz, createURL(SLASH + entity.getId()));

		assertThat(responseEntity.getBody().getId(), is(entity.getId()));

	}

	
	public ResponseEntity<T> getOne(Object content, Class<T> returnType, String url) {
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(returnType);
		HttpEntity<Object> requestEntity = new HttpEntity<>(content);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, typeRef);
		
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));
	
		return response;
	}

	public ResponseEntity<List<T>> getAll(String url, ParameterizedTypeReference<List<T>> parameterizedTypeReference) {

		ResponseEntity<List<T>> response = restTemplate.exchange(url, HttpMethod.GET, null, parameterizedTypeReference);
		return response;
	}



	public ResponseEntity<T> post(Object content, Class<T> returnType, String url) {
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(returnType);
		HttpEntity<Object> requestEntity = new HttpEntity<>(content);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, typeRef);
		return response;
	}

	public ResponseEntity<T> put(Object content, Class<T> returnType, String url, Map<String, String> params) {
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(returnType);
		HttpEntity<Object> requestEntity = new HttpEntity<>(content);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, typeRef, params);
		return response;
	}

	public ResponseEntity<T> delete(Object content, Class<T> returnType, String url) {
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(returnType);
		HttpEntity<Object> requestEntity = new HttpEntity<>(content);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, typeRef);
		return response;
	}



	protected StringBuilder createBaseUri(String uriPart) {
		return new StringBuilder().append(HOST).append(port).append(SLASH).append(APP_ROOT).append(SLASH);

	}

	protected boolean isSorted(List<T> resources, Comparator<T> comparator) {
		if (resources.isEmpty() || resources.size() == 1) {
			return true;
		}

		Iterator<T> iter = resources.iterator();
		T current, previous = iter.next();
		while (iter.hasNext()) {
			current = iter.next();
			if (comparator.compare(previous, current) < 0) {
				return false;
			}
			previous = current;
		}
		return true;
	}
	protected T createNewEntity(String uriString) {
		ResponseEntity<List<T>> responseEntity = 	 restTemplate.exchange(createURL(uriString), HttpMethod.GET, null,getParamTypeRef());
		assertFalse(responseEntity.getBody().isEmpty());
		return responseEntity.getBody().get(0);

	}

	protected String createURL(String uriPart) {
		StringBuilder uri = createBaseUri(uriPart);
		uri.append(getApiName());
		if (!uriPart.isEmpty()) {
			uri.append(uriPart);
		}
		return uri.toString();
	}
	
	protected T createNewEntityAndPersist() {
		T entity = createEntity();
		ResponseEntity<T> result = post(entity, clazz, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = result.getHeaders();
		List<String> location = headers.get(HttpHeaders.LOCATION);
		assertNotNull(location);
		ResponseEntity<T> responseEntity = restTemplate.exchange(location.get(0), HttpMethod.GET,
				defaultHttpEntity, clazz);
		return responseEntity.getBody();

	
	}
	protected abstract String getApiName();
	protected abstract T createEntity();
	protected abstract ParameterizedTypeReference<List<T>> getParamTypeRef();
	protected abstract TypeReference<List<T>> getTypeRef();
	
}