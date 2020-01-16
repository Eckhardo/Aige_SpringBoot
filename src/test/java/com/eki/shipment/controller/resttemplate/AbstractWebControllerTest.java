package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
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

import com.eki.common.interfaces.IEntity;
import com.eki.shipment.run.MysqlBootApplication;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * TestRestTemplate is only auto-configured when @SpringBootTest has been
 * configured with a webEnvironment that means it starts the web container and
 * listens for HTTP requests.
 * 
 * @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT) is mandatory
 * @author eckha
 *
 */
@SpringBootTest(classes = MysqlBootApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")

public abstract class AbstractWebControllerTest<T extends IEntity> {

	Class<T> clazz;

	Class<List<T>> clazzList;

	@SuppressWarnings("unchecked")
	public AbstractWebControllerTest(Class<T> clazz) {
		super();
		this.clazz = clazz;
	
		List<T> myList = new ArrayList<T>();
		this.clazzList =  (Class<List<T>>)myList.getClass();
	}

	@Autowired
	TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;


	@Test
	public void whenFindAll_thenResourcesAreRetrieved() throws Exception {

		ResponseEntity<List<T>> responseEntity = getAll(createURL(SLASH));
		assertFalse(responseEntity.getBody().isEmpty());
	}

	@Test
	public void whenFindOne_ThenResourceIsRetrieved() {
		T entity = createNewEntityAndPersist();
		ResponseEntity<T> responseEntity = getOne(createURL(SLASH + entity.getId()));

		assertThat(responseEntity.getBody().getId(), is(entity.getId()));

	}

	@Test
	public void whenFindAllPaged_thenResourcesArePaged() throws Exception {

		ResponseEntity<List<T>> response = getAll(createURL(QUESTION_MARK + PAGE_NO + "=1"));
		assertFalse(response.getBody().isEmpty());

	}

	@Test
	public void whenFindAllSorted_thenResourceIsSorted() throws Exception {
		ResponseEntity<List<T>> responseEntity = getAll(createURL(COMPLETE_SORT_ORDER));
		assertFalse(responseEntity.getBody().isEmpty());
	
	
	}

	protected ResponseEntity<T> getOne(String url) {
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(null), getResponseType());
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));

		return response;
	}



	protected ResponseEntity<List<T>> getAll(String url) {
		return restTemplate.exchange(url, HttpMethod.GET, null, getResponseTypeAsList());
		
	}

	protected ResponseEntity<T> post(T content, String url) {
	return restTemplate.exchange(url, HttpMethod.POST, getHttpEntity(content), getResponseType());
	
	}
	@Test
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		T entity = createEntity();
		ResponseEntity<T> result = post(entity, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = result.getHeaders();
		List<String> locations = headers.get(HttpHeaders.LOCATION);
		assertNotNull(locations);
		String location=locations.get(0);
		String id=location.substring(location.lastIndexOf(SLASH +1)).substring(1);
		ResponseEntity<T> responseEntity = restTemplate.exchange(location, HttpMethod.GET,
				getHttpEntity(null), clazz);
		T retrievedEntity = (T) responseEntity.getBody();

		assertEquals(id, retrievedEntity.getId().toString());

	}

	protected ResponseEntity<T> put(T content, String url, Map<String, String> params) {
	return restTemplate.exchange(url, HttpMethod.PUT, getHttpEntity(content), getResponseType());
	
	}

	@Test
	public void whenUpdateResource_thenStatusCodeIsOk() {
		T entity = createNewEntityAndPersist();
	
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		ResponseEntity<T> result = put(entity, createURL(SLASH + entity.getId()), params);

		assertThat(result.getStatusCode(), is(HttpStatus.OK));

	}
	protected ResponseEntity<T> delete(T content, String url) {
		return restTemplate.exchange(url, HttpMethod.DELETE, getHttpEntity(content), getResponseType());
	
	}

	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		T entity = createNewEntityAndPersist();

		ResponseEntity<T> responseEntity = delete(entity, createURL(SLASH + entity.getId().toString()));

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

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
		ResponseEntity<T> result = post(entity, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = result.getHeaders();
		List<String> location = headers.get(HttpHeaders.LOCATION);
		assertNotNull(location);
		ResponseEntity<T> responseEntity = restTemplate.exchange(location.get(0), HttpMethod.GET, getHttpEntity(null),
				clazz);
		return responseEntity.getBody();

	}

	protected abstract String getApiName();

	protected abstract T createEntity();
	protected abstract  TypeReference<List<T>> getTypeRef();
	
	
	protected  ParameterizedTypeReference<List<T>> getResponseTypeAsList(){
		return	ParameterizedTypeReference.forType(clazzList);
	}

	protected  ParameterizedTypeReference<T> getResponseType(){
	return	ParameterizedTypeReference.forType(clazz);
	}
	
	protected HttpEntity<T> getHttpEntity(T entity) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		// Request to return JSON format
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<T>(entity,headers);
		
	}
}
