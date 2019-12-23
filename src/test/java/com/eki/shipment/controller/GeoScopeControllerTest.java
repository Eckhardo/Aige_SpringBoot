package com.eki.shipment.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.shipment.dao.AbstractRepositoryTest;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.run.MysqlBootApplication;

public class GeoScopeControllerTest extends AbstractWebControllerTest {

	@Autowired(required=true)
	private TestRestTemplate restTemplate;

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
				"/preferredPort/filter?location_code=DUS&geo_scope_type=T&country_code=DE", GeoScope[].class);
		assertThat(body.length, is(equalTo(4)));

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].isPort(), is(true));
		}
	}
	@Test
	public void whenSearchPorts_thenReturnPorts() {
		GeoScope[] body = this.restTemplate.getForObject(
				"/ports/filter?location_code=ARB", GeoScope[].class);
		assertThat(body.length, is(equalTo(1)));

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].isPort(), is(true));
		}
	}
}