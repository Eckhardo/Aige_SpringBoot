package com.eki.shipment.service;

import static com.eki.shipment.util.EntityFactory.createGeoScope;
import static com.eki.shipment.util.EntityFactory.createGeoScopes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import com.eki.shipment.dao.GeoScopeRepository;
import com.eki.shipment.model.GeoScope;

public final class GeoScopeServiceUnitTest extends AbstractServiceUnitTest<GeoScope> {

	@Mock
	private GeoScopeRepository daoMock;

	@InjectMocks
	private GeoScopeService serviceUnderTest;
	
	private List<GeoScope> geoScopes;
	private GeoScope geoScope;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		geoScopes=createGeoScopes();
		geoScope=geoScopes.get(0);
	}

	@Test
	public void whenPreciseSearch_thenReturnSingleResult() {
		when(daoMock.findAll()).thenReturn(geoScopes);
		List<GeoScope> result = serviceUnderTest.searchGeoScopes(geoScope.getLocationCode(),geoScope.getGeoScopeType(),geoScope.getCountryCode());
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(geoScope));
		verify(daoMock, times(1)).findAll();
	}



	@Test
	public void whenSearchPreferredPortsDE_thenReturnResultList() {
		when(daoMock.findAll(ArgumentMatchers.<Example<GeoScope>>any())).thenReturn(geoScopes);
		List<GeoScope> result = serviceUnderTest.findPreferredGeoScopes(geoScope.getLocationCode(),geoScope.getCountryCode());
		assertThat(result.size(), is(0));
	
	
		verify(daoMock, times(1)).findAll(ArgumentMatchers.<Example<GeoScope>>any());

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
