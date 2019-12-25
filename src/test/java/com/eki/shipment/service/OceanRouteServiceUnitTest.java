package com.eki.shipment.service;

import static com.eki.util.EntityFactory.createOceanRoutes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eki.shipment.dao.OceanRouteDynamicQueryDao;
import com.eki.shipment.dao.OceanRouteRepository;
import com.eki.shipment.model.OceanRoute;
import com.eki.util.IDUtil;

public final class OceanRouteServiceUnitTest extends AbstractServiceUnitTest<OceanRoute> {

	@Mock
	private OceanRouteRepository daoMock;

	@Mock
	private OceanRouteDynamicQueryDao dynamicDaoMock;

	@InjectMocks
	OceanRouteService serviceUnderTest;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenSearchValidRoutes_thenReturnValidList() {
		when(dynamicDaoMock.findRoutes("BRSSZ", "HKHKG", null, null, null, false)).thenReturn(createNewEntities());
		List<OceanRoute> result = serviceUnderTest.searchOceanRoutes(false, false, "1", "BRSSZ", "HKHKG", null, null,
				null, null, null, null);
		assertThat(result.size(), is(2));
		verify(dynamicDaoMock, times(1)).findRoutes("BRSSZ", "HKHKG", null, null, null, false);
	}

	@Test
	public void whenSearchAllRoutes_thenReturnWholeList() {
		when(dynamicDaoMock.findRoutes("BRSSZ", "HKHKG", null, null, null, false)).thenReturn(createNewEntities());
		List<OceanRoute> result = serviceUnderTest.searchOceanRoutes(true, false, "1", "BRSSZ", "HKHKG", null, null,
				null, null, null, null);
		assertThat(result.size(), is(3));
		verify(dynamicDaoMock, times(1)).findRoutes("BRSSZ", "HKHKG", null, null, null, false);
	}

	protected List<OceanRoute> createNewEntities() {
		return createOceanRoutes();
	}

	@Override
	protected OceanRoute createNewEntity() {
		return createNewEntities().get(0);
	}

	@Override
	protected void changeEntity(OceanRoute entity) {
		// TODO Auto-generated method stub

	}

	@Override
	protected OceanRoute stubDaoGetOne(long id) {
		final OceanRoute or = createNewEntity();
		or.setId(IDUtil.randomPositiveLong());
		when(getDAO().findById(id)).thenReturn(Optional.of(or));
		return or;
	}

	@Override
	protected OceanRouteService getApi() {
		return serviceUnderTest;
	}

	@Override
	protected OceanRouteRepository getDAO() {
		return daoMock;
	}
}
