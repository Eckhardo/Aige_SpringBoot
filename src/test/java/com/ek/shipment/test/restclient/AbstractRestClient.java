package com.ek.shipment.test.restclient;

import static com.eki.common.util.QueryConstants.SLASH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.eki.common.interfaces.IEntity;
import com.google.common.base.Preconditions;

public abstract class AbstractRestClient<T extends IEntity> implements IRestClient<T> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected final Class<T> clazz;
	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate restTemplate;

	final String HOST = "http://localhost:";

	final String APP_ROOT = "nre";

	public AbstractRestClient(final Class<T> clazz) {
		Preconditions.checkNotNull(clazz);
		this.clazz = clazz;
	}

	@Override
	public final T findOne(final long id) {
		return getOneAsEntity(id);
	}

	@Override
	public final ResponseEntity<T> findOneAsResponse(final long id) {
		return getOneAsResponse(createURL(SLASH + id));
	}

	@Override
	public T create(long id) {
		return null;
	}

	@Override
	public ResponseEntity<T> createAsResponse(long id) {
		return null;
	}

	protected T getOneAsEntity(final long id) {
		ResponseEntity<T> response = getOneAsResponse(createURL(SLASH + id));
		return response.getBody();
	}

	protected ResponseEntity<T> getOneAsResponse(String url) {
		ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(clazz);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, getEntityWithHeader(), typeRef);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));

		return response;
	}

	private HttpEntity<String> getEntityWithHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		// Request to return JSON format
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}

	protected String createURL(String uriPart) {
		StringBuilder uri = createBaseUri(uriPart);
		uri.append(getApiName());
		if (!uriPart.isEmpty()) {
			uri.append(uriPart);
		}
		return uri.toString();
	}

	protected StringBuilder createBaseUri(String uriPart) {
		return new StringBuilder().append(HOST).append(port).append(SLASH).append(APP_ROOT).append(SLASH);

	}

	protected abstract String getApiName();

	protected abstract T createEntity();
}
