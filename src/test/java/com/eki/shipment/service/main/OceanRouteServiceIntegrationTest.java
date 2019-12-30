package com.eki.shipment.service.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eki.shipment.dao.OceanRouteDynamicQueryDao;
import com.eki.shipment.model.OceanRoute;
import com.eki.shipment.service.IServiceOperations;
import com.eki.shipment.service.OceanRouteService;
import com.eki.shipment.util.EntityFactory;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import org.junit.Test;


public class OceanRouteServiceIntegrationTest extends AbstractServiceIntegrationTest<OceanRoute> {

	@Autowired
	OceanRouteService serviceUnderTest;
	@Autowired
	private OceanRouteDynamicQueryDao dynamicQueryDao;

	@Test
	public void whenDynamicSearch_thenReturnResult() {
		OceanRoute entity = persistNewEntity();
		List<OceanRoute> result = dynamicQueryDao.findRoutes(entity.getPol(), entity.getPod(), entity.getTs1(),
				entity.getTs2(), entity.getTs3(), entity.isShunting());
	}

	@Override
	protected void invalidate(OceanRoute invalidResource) {
		invalidResource.setPol(null);

	}

	@Override
	protected void change(OceanRoute resource) {
		resource.setPol(randomAlphabetic(5));

	}

	@Override
	protected OceanRoute createNewEntity() {
		OceanRoute entity = EntityFactory.createOceanRoute();

		return entity;
	}

	@Override
	protected OceanRouteService getApi() {
		return serviceUnderTest;
	}

}
