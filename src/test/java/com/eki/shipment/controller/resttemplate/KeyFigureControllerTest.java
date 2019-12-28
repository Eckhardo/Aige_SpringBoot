package com.eki.shipment.controller.resttemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.eki.common.interfaces.IEntity;
import com.eki.shipment.model.KeyFigure;

public class KeyFigureControllerTest extends AbstractWebControllerTest<KeyFigure>{

	
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

	@Override
	public boolean isSorted(List<KeyFigure> resources, Comparator<KeyFigure> comparator) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getApiName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected KeyFigure createEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createURL(String uriPart) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	

}
