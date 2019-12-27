package com.eki.shipment.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.service.GeoScopeService;
import com.eki.shipment.service.KeyFigureService;
import com.eki.shipment.util.IDUtil;

public class KeyFigureServiceIntegrationTest extends AbstractServiceIntegrationTest<KeyFigure> {

	@Autowired
	KeyFigureService serviceUnderTest;

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

		GeoScope pol = getAssociationService().create(new GeoScope(randomAlphabetic(2), randomAlphabetic(2),
				randomAlphabetic(2), randomAlphabetic(10), true));
		GeoScope pod = getAssociationService().create(new GeoScope(randomAlphabetic(2), randomAlphabetic(2),
				randomAlphabetic(2), randomAlphabetic(10), true));
		KeyFigure kf = new KeyFigure(pol, null, pod, IDUtil.randomPositiveLong(), randomAlphabetic(2), true,
				randomAlphabetic(2), randomAlphabetic(2), null, randomAlphabetic(3), 1, randomAlphabetic(1), null);

		return kf;
	}

	@Override
	protected KeyFigureService getApi() {
		return serviceUnderTest;
	}

	final GeoScopeService getAssociationService() {
		return geoScopeService;
	}
}
