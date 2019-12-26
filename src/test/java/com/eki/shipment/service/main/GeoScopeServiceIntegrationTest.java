package com.eki.shipment.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.springframework.beans.factory.annotation.Autowired;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.service.GeoScopeService;
public class GeoScopeServiceIntegrationTest extends AbstractServiceIntegrationTest<GeoScope> {

	
	
	@Autowired
	
	GeoScopeService serviceUnderTest;
	@Override
	protected void invalidate(GeoScope invalidResource) {
		invalidResource.setCountryCode(randomAlphabetic(10));
		
	}

	@Override
	protected void change(GeoScope resource) {
	resource.setName(randomAlphabetic(6));
		
	}

	@Override
	protected GeoScope createNewEntity() {
	final GeoScope entity =new  GeoScope(randomAlphabetic(2), randomAlphabetic(3), randomAlphabetic(1), randomAlphabetic(3), false);
	return entity;
	}

	@Override
	protected GeoScopeService getApi() {
		return serviceUnderTest;
	}

}
