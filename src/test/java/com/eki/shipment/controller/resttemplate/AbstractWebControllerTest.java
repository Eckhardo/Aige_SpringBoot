package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.APP_ROOT;
import static com.eki.common.util.QueryConstants.COMPLETE_SORT_ORDER;
import static com.eki.common.util.QueryConstants.HOST;
import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.PAGE_NO;
import static com.eki.common.util.QueryConstants.QUESTION_MARK;
import static com.eki.common.util.QueryConstants.SLASH;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
@TestPropertySource(locations = "classpath:application.properties")

public abstract class AbstractWebControllerTest<E extends IEntity, D extends IDto> {

	Class<D> clazz;

	Class<List<D>> clazzList;
	
	
	@Autowired
	 ModelMapper modelMapper;
	
	

	@SuppressWarnings("unchecked")
	public AbstractWebControllerTest(Class<D> clazz) {
		super();
		this.clazz = clazz;

		List<D> myList = new ArrayList<D>();
		this.clazzList = (Class<List<D>>) myList.getClass();
	}

	@Autowired
	TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void whenFindAll_thenResourcesAreRetrieved() throws Exception {

		ResponseEntity<List<D>> responseEntity = getAll(createURL(SLASH));
		assertFalse(responseEntity.getBody().isEmpty());
	}

	@Test
	public void whenFindOne_ThenResourceIsRetrieved() {
		D resource = createAndRetrieveEntity();
		ResponseEntity<D> responseEntity = getOne(createURL(SLASH + resource.getId()));

		assertThat(responseEntity.getBody().getId(), is(resource.getId()));

	}

	@Test
	public void whenFindAllPaged_thenResourcesArePaged() throws Exception {

		ResponseEntity<List<D>> response = getAll(createURL(QUESTION_MARK + PAGE_NO + "=1"));
		assertFalse(response.getBody().isEmpty());

	}

	@Test
	public void whenFindAllSorted_thenResourceIsSorted() throws Exception {
		ResponseEntity<List<D>> responseEntity = getAll(createURL(COMPLETE_SORT_ORDER));
		assertFalse(responseEntity.getBody().isEmpty());

	}

	@Test
	public void whenCreateNew_thenTheNewResourceIsRetrievableByLocationHeader() {
		String uri = createAsUri();
		ResponseEntity<D> responseEntity = getOne(uri);
		D retrievedEntity = (D) responseEntity.getBody();
		assertThat(retrievedEntity.getId(), notNullValue());

	}

	@Test
	public void whenUpdateResource_thenStatusCodeIsOk() {
		D resource = createAndRetrieveEntity();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, resource.getId().toString());
		ResponseEntity<D> result = put(resource, createURL(SLASH + resource.getId()), params);

		assertThat(result.getStatusCode(), is(HttpStatus.OK));

	}

	@Test
	public void whenResourceIsUpdatedWithNullId_then400IsReceived() {
		// When
		D resource = createAndRetrieveEntity();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ID, resource.getId().toString());
		resource.setId(null);
		ResponseEntity<D> result = put(resource, createURL(SLASH + resource.getId()), params);

		// Then
		assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void whenDeleteResourse_thenStatusCodeIsNoContent() {
		D resource = createAndRetrieveEntity();
		ResponseEntity<D> responseEntity = delete(resource, createURL(SLASH + resource.getId().toString()));
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

	}

	@Test
	public void givenResourceExistedAndWasDeleted_whenRetrievingResource_then404IsReceived() {
		// Given
		D resource = createAndRetrieveEntity();
		

		ResponseEntity<D> responseEntity =	delete(resource, createURL(SLASH + resource.getId().toString()));
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
		// When
		ResponseEntity<D> responseEntityRead = getOne(createURL(SLASH + resource.getId()));

		// Then
		assertThat(responseEntityRead.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	@Test
	public void whenResourceIsDeletedByIncorrectNonNumericId_then400IsReceived() {
		D resource = createAndRetrieveEntity();
		resource.setId(Long.parseLong(randomNumeric(6)));
		ResponseEntity<D> responseEntity = delete(resource, createURL(SLASH + resource.getId().toString()));
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

	protected ResponseEntity<D> getOne(String url) {
		return restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(null), getResponseType());

	}

	protected ResponseEntity<List<D>> getAll(String url) {
		return restTemplate.exchange(url, HttpMethod.GET, null, getResponseTypeAsList());

	}

	protected ResponseEntity<D> post(D content, String url) {
		return restTemplate.exchange(url, HttpMethod.POST, getHttpEntity(content), getResponseType());

	}

	protected ResponseEntity<D> put(D content, String url, Map<String, String> params) {
		return restTemplate.exchange(url, HttpMethod.PUT, getHttpEntity(content), getResponseType());

	}

	protected ResponseEntity<D> delete(D content, String url) {
		return restTemplate.exchange(url, HttpMethod.DELETE, getHttpEntity(content), getResponseType());

	}

	protected String createAsUri() {

		final ResponseEntity<D> result = createAsResponse();
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		final HttpHeaders headers = result.getHeaders();
		final List<String> locations = headers.get(HttpHeaders.LOCATION);
		assertNotNull(locations);
		return locations.get(0);

	}

	protected ResponseEntity<D> createAsResponse() {

		final D resource = convertToDto(createEntity());
		ResponseEntity<D> result = post(resource, createURL(SLASH));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		return result;
	}

	protected D createAndRetrieveEntity() {

		String uri = createAsUri();
		ResponseEntity<D> responseEntity = getOne(uri);
		return (D) responseEntity.getBody();

	}

	protected ParameterizedTypeReference<List<D>> getResponseTypeAsList() {
		return ParameterizedTypeReference.forType(clazzList);
	}

	protected ParameterizedTypeReference<D> getResponseType() {
		return ParameterizedTypeReference.forType(clazz);
	}

	protected abstract TypeReference<List<D>> getTypeRef();


	protected HttpEntity<D> getHttpEntity(D resource) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<D>(resource, headers);

	}

	protected abstract String getApiName();

	protected abstract E createEntity();

	protected abstract D convertToDto(E entity);
	
	protected abstract E convertToEntity(D dto);
	
}
