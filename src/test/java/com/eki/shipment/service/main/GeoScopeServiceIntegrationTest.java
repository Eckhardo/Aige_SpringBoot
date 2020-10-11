package com.eki.shipment.service.main;

import static com.eki.shipment.util.EntityFactory.createGeoScope;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.function.Predicate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.service.GeoScopeService;


public class GeoScopeServiceIntegrationTest extends AbstractServiceIntegrationTest<GeoScope> {

	@Autowired
	GeoScopeService serviceUnderTest;

	@Test
	public void whenSaveIsPerformed_thenNoException() {
		getApi().create(createNewEntity());
	}

	@Test
	public void whenFindPorts_OnlyPortLocationsAreRetrieved() {
		Collection<GeoScope> ports = serviceUnderTest.findPorts(createGeoScope().getCountryCode());
		Predicate<GeoScope> isPort = GeoScope::isPort;
		assertTrue(ports.stream().allMatch(isPort));
	}

	@Test
	public void whenFindPreferredGeoScopes_thenOnlyPortLocationsAreRetrieved() {
		Collection<GeoScope> preferredPorts = serviceUnderTest
				.findPreferredGeoScopes(createGeoScope().getLocationCode(), "");
		Predicate<GeoScope> isPort = g -> g.isPort();
		assertTrue(preferredPorts.stream().allMatch(isPort));
	}

	@Override
	protected void invalidate(GeoScope invalidResource) {
		invalidResource.setCountryCode(null);

	}

	@Override
	protected void change(GeoScope resource) {
		resource.setName(randomAlphabetic(6));

	}

	@Override
	protected GeoScope createNewEntity() {
		final GeoScope entity = new GeoScope(randomAlphabetic(2), randomAlphabetic(3), randomAlphabetic(1),
				randomAlphabetic(3), false);
		return entity;
	}

	@Override
	protected GeoScopeService getApi() {
		return serviceUnderTest;
	}

}
