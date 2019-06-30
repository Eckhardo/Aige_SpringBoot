package com.eki.service;

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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.eki.model.OceanRoute;
import com.eki.oceanroute.OceanRouteDynamicQueryDao;
import com.eki.oceanroute.OceanRouteRepository;

@RunWith(MockitoJUnitRunner.class)
public class OceanRouteServiceTest {

	

	@Mock
	private OceanRouteRepository oceanRepository;

	

	@Mock
	private OceanRouteDynamicQueryDao oceanDynamicQueryDao;
	
	@InjectMocks
	OceanRouteService oceanService;

	private List<OceanRoute> oceanRoutes = new ArrayList<>();

	@Before
	public void init() {
			oceanService.setOceanRepository(oceanRepository);
		oceanService.setOceanDynamicQueryDao(oceanDynamicQueryDao);
		
		OceanRoute simple= new OceanRoute("BRSSZ", "HKHKG", null, new ArrayList<>());
		OceanRoute withTsPort= new OceanRoute("BRSSZ", "HKHKG", "ARBUE", new ArrayList<>());
		OceanRoute withErrors= new OceanRoute("BRSSZ", "HKHKG", "ARBUE", Arrays.asList("NO_SHUNTING"));
		oceanRoutes.add(simple);
		oceanRoutes.add(withTsPort);
		oceanRoutes.add(withErrors);
	}
	
	@Test
	public void whenSearchValidRoutes_thenReturnValidList() {
		when(oceanDynamicQueryDao.findRoutes("BRSSZ", "HKHKG", null, null, null, false)).thenReturn(oceanRoutes);
		List<OceanRoute> result = oceanService.searchOceanRoutes(false, false, "1", "BRSSZ", "HKHKG", null, null, null, null, null, null);
		assertThat(result.size(), is(2));
		assertThat(result.get(0), is(oceanRoutes.get(0)));
		verify(oceanDynamicQueryDao, times(1)).findRoutes("BRSSZ", "HKHKG", null, null, null, false);
	}
	@Test
	public void whenSearchAllRoutes_thenReturnWholeList() {
		when(oceanDynamicQueryDao.findRoutes("BRSSZ", "HKHKG", null, null, null, false)).thenReturn(oceanRoutes);
		List<OceanRoute> result = oceanService.searchOceanRoutes(true, false, "1", "BRSSZ", "HKHKG", null, null, null, null, null, null);
		assertThat(result.size(), is(3));
		assertThat(result.get(0), is(oceanRoutes.get(0)));
		verify(oceanDynamicQueryDao, times(1)).findRoutes("BRSSZ", "HKHKG", null, null, null, false);
	}
}
