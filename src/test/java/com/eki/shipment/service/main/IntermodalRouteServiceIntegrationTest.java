package com.eki.shipment.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.service.GeoScopeService;
import com.eki.shipment.service.IntermodalRouteService;
import com.eki.shipment.util.EntityFactory;
import com.eki.shipment.util.IDUtil;


public class IntermodalRouteServiceIntegrationTest extends AbstractServiceIntegrationTest<KeyFigure> {

	@Autowired
	IntermodalRouteService serviceUnderTest;

	@Autowired
	GeoScopeService geoScopeService;

	@Test
	public void whenSaveIsPerformed_thenNoException() {
		getApi().create(createNewEntity());
	}

	@Override
	protected void invalidate(KeyFigure invalidResource) {
		invalidResource.setTransportMode(null);

	}

	@Override
	protected void change(KeyFigure resource) {
		resource.setTransportMode(randomAlphabetic(4));

	}

	@Override
	protected KeyFigure createNewEntity() {

		GeoScope from = getAssociationService().create(new GeoScope(randomAlphabetic(2), randomAlphabetic(2),
				randomAlphabetic(2), randomAlphabetic(10), true));
		GeoScope to = getAssociationService().create(new GeoScope(randomAlphabetic(2), randomAlphabetic(2),
				randomAlphabetic(2), randomAlphabetic(10), true));
		return EntityFactory.createIntermodalRoute(from, to);

	}

	@Override
	protected IntermodalRouteService getApi() {
		return serviceUnderTest;
	}

	final GeoScopeService getAssociationService() {
		return geoScopeService;
	}
}
