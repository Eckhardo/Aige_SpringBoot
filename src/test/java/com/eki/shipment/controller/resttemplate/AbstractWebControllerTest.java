package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.*;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.Matchers.notNullValue;
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
import org.modelmapper.ModelMapper;
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

import com.eki.common.interfaces.IDto;
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

public abstract class AbstractWebControllerTest<E extends IEntity, D extends IDto> {

	Class<E> clazz;

	Class<List<E>> clazzList;
	
	
	@Autowired
	 ModelMapper modelMapper;
	
	

	@SuppressWarnings("unchecked")
	public AbstractWebControllerTest(Class<E> clazz) {
		super();
		this.clazz = clazz;

		List<E> myList = new ArrayList<E>();
		this.clazzList = (Class<List<E>>) myList.getClass();
	}

	@Autowired
	TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void whenFindAll_thenResourcesAreRetrieved() throws Exception {

		ResponseEntity<List<E>> responseEntity = getAll(createURL(SLASH));
		assertFalse(responseEntity.getBody().isEmpty());
	}

	@Test
	public void whenFindOne_ThenResourceIsRetrieved() {
		E entity = createAndRetrieveEntity();
		ResponseEntity<E> responseEntity = getOne(createURL(SLASH + entity.getId()));

		assertThat(responseEntity.getBody().getId(), is(entity.getId()));

	}

	@Test
	public void whenFindAllPaged_thenResourcesArePaged() throws Exception {

		ResponseEntity<List<E>> response = getAll(createURL(QUESTION_MARK + PAGE_NO + "=1"));
		assertFalse(response.getBody().isEmpty());

	}

	@Test
	public void whenFindAllSorted_thenResourceIsSorted() throws Exception {
		ResponseEntity<List<E>> responseEntity = getAll(createURL(COMPLETE_SORT_ORDER));
		assertFalse(responseEntity.getBody().isEmpty());

	}

	@Test
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		String uri = createAsUri();
		ResponseEntity<E> responseEntity = getOne(uri);
		E retrievedEntity = (E) responseEntity.getBody();
		assertThat(retrievedEntity.getId(), notNullValue());

	}

	@Test
	public void whenUpdateResource_thenStatusCodeIsOk() {
		E entity = createAndRetrieveEntity();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		ResponseEntity<E> result = put(entity, createURL(SLASH + entity.getId()), params);

		assertThat(result.getStatusCode(), is(HttpStatus.OK));

	}

	@Test
	public void whenResourceIsUpdatedWithNullId_then400IsReceived() {
		// When
		E entity = createAndRetrieveEntity();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, entity.getId().toString());
		entity.setId(null);
		ResponseEntity<E> result = put(entity, createURL(SLASH + entity.getId()), params);

		// Then
		assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		E entity = createAndRetrieveEntity();
		ResponseEntity<E> responseEntity = delete(entity, createURL(SLASH + entity.getId().toString()));
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}

	@Test
	public void givenResourceExistedAndWasDeleted_whenRetrievingResource_then404IsReceived() {
		// Given
		E entity = createAndRetrieveEntity();
		

		ResponseEntity<E> responseEntity =	delete(entity, createURL(SLASH + entity.getId().toString()));
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
		// When
		ResponseEntity<E> responseEntityRead = getOne(createURL(SLASH + entity.getId()));

		// Then
		assertThat(responseEntityRead.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	@Test
	public void whenResourceIsDeletedByIncorrectNonNumericId_then400IsReceived() {
		E entity = createAndRetrieveEntity();
		entity.setId(Long.parseLong(randomNumeric(6)));
		ResponseEntity<E> responseEntity = delete(entity, createURL(SLASH + entity.getId().toString()));
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
	
	}

	protected StringBuilder createBaseUri(String uriPart) {
		return new StringBuilder().append(HOST).append(port).append(SLASH).append(APP_ROOT).append(SLASH);

	}

	protected boolean isSorted(List<E> resources, Comparator<E> comparator) {
		if (resources.isEmpty() || resources.size() == 1) {
			return true;
		}

		Iterator<E> iter = resources.iterator();
		E current, previous = iter.next();
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

	protected ResponseEntity<E> getOne(String url) {
		return restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(null), getResponseType());

	}

	protected ResponseEntity<List<E>> getAll(String url) {
		return restTemplate.exchange(url, HttpMethod.GET, null, getResponseTypeAsList());

	}

	protected ResponseEntity<E> post(E content, String url) {
		return restTemplate.exchange(url, HttpMethod.POST, getHttpEntity(content), getResponseType());

	}

	protected ResponseEntity<E> put(E content, String url, Map<String, String> params) {
		return restTemplate.exchange(url, HttpMethod.PUT, getHttpEntity(content), getResponseType());

	}

	protected ResponseEntity<E> delete(E content, String url) {
		return restTemplate.exchange(url, HttpMethod.DELETE, getHttpEntity(content), getResponseType());

	}

	protected String createAsUri() {

		final ResponseEntity<E> result = createAsResponse();
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		final HttpHeaders headers = result.getHeaders();
		final List<String> locations = headers.get(HttpHeaders.LOCATION);
		assertNotNull(locations);
		return locations.get(0);

	}

	protected ResponseEntity<E> createAsResponse() {

		final E entity = createEntity();
		ResponseEntity<E> result = post(entity, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		return result;
	}

	protected E createAndRetrieveEntity() {

		String uri = createAsUri();
		ResponseEntity<E> responseEntity = getOne(uri);
		return (E) responseEntity.getBody();

	}

	protected ParameterizedTypeReference<List<E>> getResponseTypeAsList() {
		return ParameterizedTypeReference.forType(clazzList);
	}

	protected ParameterizedTypeReference<E> getResponseType() {
		return ParameterizedTypeReference.forType(clazz);
	}

	protected abstract TypeReference<List<E>> getTypeRef();


	protected HttpEntity<E> getHttpEntity(E entity) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<E>(entity, headers);

	}

	protected abstract String getApiName();

	protected abstract E createEntity();

	protected abstract D convertToDto(E entity);
	
	protected abstract E convertToEntity(D dto);
	
}
