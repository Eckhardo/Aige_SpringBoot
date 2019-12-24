package com.eki.shipment.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.eki.shipment.dao.OceanRouteDynamicQueryDao;
import com.eki.shipment.dao.OceanRouteRepository;
import com.eki.shipment.model.OceanRoute;

@RunWith(MockitoJUnitRunner.class)
public class OceanRouteServiceTest {

	@Mock
	private OceanRouteRepository oceanRepository;

	@Mock
	private OceanRouteDynamicQueryDao oceanDynamicQueryDao;
	
	@InjectMocks
	OceanRouteService oceanService;

	private List<OceanRoute> routes = new ArrayList<>();

	@Before
	public void init() {
			oceanService.setOceanRepository(oceanRepository);
		oceanService.setOceanDynamicQueryDao(oceanDynamicQueryDao);
		
		OceanRoute simple= new OceanRoute("BRSSZ", "HKHKG", null, new ArrayList<>());
		OceanRoute withTsPort= new OceanRoute("BRSSZ", "HKHKG", "ARBUE", new ArrayList<>());
		OceanRoute withErrors= new OceanRoute("BRSSZ", "HKHKG", "ARBUE", Arrays.asList("NO_SHUNTING"));
		routes.add(simple);
		routes.add(withTsPort);
		routes.add(withErrors);
	}
	
	@Test
	public void whenSearchValidRoutes_thenReturnValidList() {
		when(oceanDynamicQueryDao.findRoutes("BRSSZ", "HKHKG", null, null, null, false)).thenReturn(routes);
		List<OceanRoute> result = oceanService.searchOceanRoutes(false, false, "1", "BRSSZ", "HKHKG", null, null, null, null, null, null);
		assertThat(result.size(), is(2));
		assertThat(result.get(0), is(routes.get(0)));
		verify(oceanDynamicQueryDao, times(1)).findRoutes("BRSSZ", "HKHKG", null, null, null, false);
	}
	@Test
	public void whenSearchAllRoutes_thenReturnWholeList() {
		when(oceanDynamicQueryDao.findRoutes("BRSSZ", "HKHKG", null, null, null, false)).thenReturn(routes);
		List<OceanRoute> result = oceanService.searchOceanRoutes(true, false, "1", "BRSSZ", "HKHKG", null, null, null, null, null, null);
		assertThat(result.size(), is(3));
		assertThat(result.get(0), is(routes.get(0)));
		verify(oceanDynamicQueryDao, times(1)).findRoutes("BRSSZ", "HKHKG", null, null, null, false);
	}
}
