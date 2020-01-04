package com.eki.shipment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ek.shipment.test.restclient.CountryRestClient;
import com.eki.shipment.model.Country;
import com.eki.shipment.run.MysqlBootApplication;

@SpringBootTest(classes = MysqlBootApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@EntityScan(basePackages = "com.eki")
public class CountryControllerLiveTest extends AbstractControllerLiveTest<Country> {

//	@Autowired
//	private CountryRestClient api;

	public CountryControllerLiveTest() {
		super(Country.class);

	}
	
	@Test
	public void testDummy() {
		
	}

//	@Test
	public void whenFindOne_ThenResourceIsRetrieved() {
		Country entity = this.getApi().findOne(4L);

		assertThat(entity, is(4L));

	}

	@Override
	protected final CountryRestClient getApi() {
		return null;
	}

}
