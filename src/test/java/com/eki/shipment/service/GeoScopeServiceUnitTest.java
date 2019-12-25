package com.eki.shipment.service;

import static com.eki.shipment.util.EntityFactory.createGeoScope;
import static com.eki.shipment.util.EntityFactory.createGeoScopes;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import com.eki.shipment.dao.GeoScopeRepository;
import com.eki.shipment.model.GeoScope;

public class GeoScopeServiceUnitTest extends AbstractServiceUnitTest<GeoScope> {

	@Mock
	private GeoScopeRepository daoMock;

	@InjectMocks
	private GeoScopeService serviceUnderTest;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenPreciseSearch_thenReturnSingleResult() {
		when(daoMock.findAll()).thenReturn(createGeoScopes());
		List<GeoScope> result = serviceUnderTest.searchGeoScopes("DEHAM", "L", "DE");
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(createGeoScopes().get(0)));
		verify(daoMock, times(1)).findAll();
	}

	@Test
	public void whenImpreciseSearch_thenReturnResultList() {
		when(daoMock.findAll()).thenReturn(createGeoScopes());
		List<GeoScope> result = serviceUnderTest.searchGeoScopes("DE", "L", null);
		assertThat(result.size(), is(3));
		List<String> codes = result.stream().map((gs) -> gs.getLocationCode()).collect(Collectors.toList());

		assertThat(codes, hasItems("DEHAM", "DEDUS", "DEDUI"));

	}

	@Test
	public void whenSearchPreferredPortsDE_thenReturnResultList() {
		when(daoMock.findAll(ArgumentMatchers.<Example<GeoScope>>any())).thenReturn(createGeoScopes());
		List<GeoScope> result = serviceUnderTest.findPreferredGeoScopes("DEDUS", "DE");
		assertThat(result.size(), is(3));
		List<String> codes = result.stream().map((gs) -> gs.getLocationCode()).collect(Collectors.toList());

		assertThat(codes, hasItems("DEHAM", "DEDUS", "DEDUI"));
		verify(daoMock, times(1)).findAll(ArgumentMatchers.<Example<GeoScope>>any());

	}

	@Test
	public void whenSearchPreferredPortsBR_thenReturnResultList() {
		when(daoMock.findAll(ArgumentMatchers.<Example<GeoScope>>any())).thenReturn(createGeoScopes());
		List<GeoScope> result = serviceUnderTest.findPreferredGeoScopes("BRSSZ", "BR");
		assertThat(result.size(), is(1));
		List<String> codes = result.stream().map((gs) -> gs.getLocationCode()).collect(Collectors.toList());

		assertThat(codes, hasItems("BRSSZ"));

	}

	@Override
	protected GeoScope createNewEntity() {
		return createGeoScope();
	}



	@Override
	protected void changeEntity(GeoScope entity) {
		// TODO Auto-generated method stub

	}

	@Override
	protected GeoScope stubDaoGetOne(long id) {
		final GeoScope entity = createNewEntity();
		entity.setId(id);
		when(daoMock.findById(id)).thenReturn(Optional.of(entity));
		return entity;
	}

	@Override
	protected IServiceOperations<GeoScope> getApi() {
		return serviceUnderTest;
	}

	@Override
	protected GeoScopeRepository getDAO() {
		return daoMock;
	}
}
