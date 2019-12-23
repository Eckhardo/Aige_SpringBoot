package com.eki.shipment.service;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import com.eki.shipment.dao.GeoScopeRepository;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.service.GeoScopeService;

public class GeoScopeServiceTest {


	@Mock
	private GeoScopeRepository geoScopeRepository;
	
	@InjectMocks
	private GeoScopeService geoScopeService;


	private List<GeoScope> geoScopes = new ArrayList<>();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		geoScopeService.setGeoScopeRepository(geoScopeRepository);

		GeoScope deham = new GeoScope();
		deham.setCountryCode("DE");
		deham.setLocationCode("DEHAM");
		deham.setGeoScopeType("L");
		geoScopes.add(deham);

		GeoScope dedus = new GeoScope();
		dedus.setCountryCode("DE");
		dedus.setLocationCode("DEDUS");
		dedus.setGeoScopeType("L");
		geoScopes.add(dedus);

		GeoScope dedui = new GeoScope();
		dedui.setCountryCode("DE");
		dedui.setLocationCode("DEDUI");
		dedui.setGeoScopeType("L");
		geoScopes.add(dedui);

		GeoScope brssz = new GeoScope();
		brssz.setCountryCode("BR");
		brssz.setLocationCode("BRSSZ");
		brssz.setGeoScopeType("L");
		geoScopes.add(brssz);
	}

	@Test
	public void whenPreciseSearch_thenReturnSingleResult() {
		when(geoScopeRepository.findAll()).thenReturn(geoScopes);
		List<GeoScope> result = geoScopeService.searchGeoScopes("DEHAM", "L", "DE");
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(geoScopes.get(0)));
		verify(geoScopeRepository, times(1)).findAll();
	}

	@Test
	public void whenImpreciseSearch_thenReturnResultList() {
		when(geoScopeRepository.findAll()).thenReturn(geoScopes);
		List<GeoScope> result = geoScopeService.searchGeoScopes("DE", "L", null);
		assertThat(result.size(), is(3));
		List<String> codes = result.stream().map((gs) -> gs.getLocationCode()).collect(Collectors.toList());

		assertThat(codes, hasItems("DEHAM", "DEDUS", "DEDUI"));

	}

	@Test
	public void whenSearchPreferredPortsDE_thenReturnResultList() {
		when(geoScopeRepository.findAll(Mockito.any(Example.class))).thenReturn(geoScopes);
		List<GeoScope> result = geoScopeService.findPreferredGeoScopes("DEDUS", "DE");
		assertThat(result.size(), is(3));
		List<String> codes = result.stream().map((gs) -> gs.getLocationCode()).collect(Collectors.toList());

		assertThat(codes, hasItems("DEHAM", "DEDUS", "DEDUI"));
		verify(geoScopeRepository, times(1)).findAll(Mockito.any(Example.class));
		

	}
	@Test
	public void whenSearchPreferredPortsBR_thenReturnResultList() {
		when(geoScopeRepository.findAll(Mockito.any(Example.class))).thenReturn(geoScopes);
		List<GeoScope> result = geoScopeService.findPreferredGeoScopes("BRSSZ", "BR");
		assertThat(result.size(), is(1));
		List<String> codes = result.stream().map((gs) -> gs.getLocationCode()).collect(Collectors.toList());

		assertThat(codes, hasItems("BRSSZ"));

	}
}