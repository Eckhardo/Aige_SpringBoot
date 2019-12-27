package com.eki.shipment.service.main;

import org.springframework.beans.factory.annotation.Autowired;

import com.eki.shipment.model.OceanRoute;
import com.eki.shipment.service.IServiceOperations;
import com.eki.shipment.service.OceanRouteService;
import com.eki.shipment.util.EntityFactory;

public class OceanRouteServiceIntegrationTest extends AbstractServiceIntegrationTest<OceanRoute> {

	@Autowired
	OceanRouteService serviceUnderTest;
	
	@Override
	protected void invalidate(OceanRoute invalidResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void change(OceanRoute invalidResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected OceanRoute createNewEntity() {
	OceanRoute entity =EntityFactory.createOceanRoute();
	
	return entity;
	}

	@Override
	protected OceanRouteService getApi() {
		// TODO Auto-generated method stub
		return serviceUnderTest;
	}

}
