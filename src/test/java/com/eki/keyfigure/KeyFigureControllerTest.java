package com.eki.keyfigure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.Country;
import com.eki.model.KeyFigure;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class KeyFigureControllerTest {

	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void GetKeyFiguresTest() {
		KeyFigure[] body =  this.restTemplate.getForObject("/keyfigure/find?inlandLocation=DUSSELDORF&inlandGeoScopeType=T&countryCode=DE&portLocation=DEHAM&includeAllPrefPorts=true", KeyFigure[].class);
		assertThat(body.length, is(16));
		for (int i = 0; i < body.length; i++) {
			KeyFigure kf=body[i];
			assertThat(kf.getFrom().getLocationCode(), is("DUSSELDORF"));
		}
	}
	

	

}
