package com.eki.country;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.Country;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountryControllerTest {

	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void singleCountryTest() {
		Country body =  this.restTemplate.getForObject("/country/find?country_code=DE", Country.class);
		assertThat(body.getName(), is("Germany"));
	}
	

	@Test
	public void likeCountryTest() {
		Country[] body =  this.restTemplate.getForObject("/country/filter?country_code=D", Country[].class);
		assertThat(body.length, is(2));
	}

}
