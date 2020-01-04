package com.eki.shipment.controller.resttemplate;

import static com.eki.common.util.QueryConstants.COMPLETE_SORT_ORDER;
import static com.eki.common.util.QueryConstants.PAGE_NO;
import static com.eki.common.util.QueryConstants.QUESTION_MARK;
import static com.eki.common.util.QueryConstants.SLASH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

	public AbstractWebControllerTest(Class clazz) {
		super();
		this.clazz = clazz;
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
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(clazz);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		// Request to return JSON format
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, typeRef);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));

		return response;
	}

	protected ResponseEntity<List<T>> getAll(String url) {

		ResponseEntity<List<T>> response = restTemplate.exchange(url, HttpMethod.GET, null, getResponseType());
		return response;
	}

	protected ResponseEntity<T> post(Object content, String url) {
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(clazz);
		HttpEntity<Object> requestEntity = new HttpEntity<>(content);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, typeRef);
		return response;
	}

	protected ResponseEntity<T> put(Object content, String url, Map<String, String> params) {
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(clazz);
		HttpEntity<Object> requestEntity = new HttpEntity<>(content);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, typeRef, params);
		return response;
	}

	protected ResponseEntity<T> delete(Object content, String url) {
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(clazz);
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
		ResponseEntity<T> responseEntity = restTemplate.exchange(location.get(0), HttpMethod.GET, defaultHttpEntity,
				clazz);
		return responseEntity.getBody();

	}

	protected abstract String getApiName();

	protected abstract T createEntity();

	protected abstract ParameterizedTypeReference<List<T>> getResponseType();

	protected abstract TypeReference<List<T>> getTypeRef();

}
