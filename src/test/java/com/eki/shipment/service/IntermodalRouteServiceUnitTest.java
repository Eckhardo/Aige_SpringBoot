package com.eki.shipment.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.eki.shipment.dao.IntermodalRouteDynamicQueryDao;
import com.eki.shipment.dao.IntermodalRouteRepository;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.util.IDUtil;

import data.GeoScopeData;
import data.IntermodalRouteFigureData;

public class IntermodalRouteServiceUnitTest extends AbstractServiceUnitTest<KeyFigure>{

	@Mock
	IntermodalRouteRepository daoMock;
	@Mock
	IntermodalRouteDynamicQueryDao dynamicDao;
	@InjectMocks
	IntermodalRouteService serviceUnderTest;

	List<KeyFigure> kfList;
	List<GeoScope> gsList;
	List<GeoScope> portList;
	List<String> preferredPorts;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		kfList = IntermodalRouteFigureData.getData();
		gsList = GeoScopeData.getData();

		portList = gsList.stream().filter(gs -> gs.isPort()).collect(Collectors.toList());
		assertThat(portList, (everyItem(HasPropertyWithValue.hasProperty("port", is(true)))));
		preferredPorts = portList.stream().map(gs -> gs.getLocationCode()).collect(Collectors.toList());
		assertFalse(preferredPorts.isEmpty());

	}

	@Test
	public void whenSearchKeyFigures_thenReturnNonEmptyResult() {
		when(dynamicDao.searchKeyFigures(Mockito.eq("DEDUS"), Mockito.anyString(), Mockito.anyString(),
				Mockito.eq(preferredPorts), Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(kfList);
		List<KeyFigure> result = serviceUnderTest.searchKeyFigures("DEDUS", "", "", preferredPorts, "", true, true, "", null);
		assertFalse(result.isEmpty());
	}



	@Test
	public void whenFindAll_thenReturnNonEmptyResult() {
		when(daoMock.findAll()).thenReturn(kfList);
		List<KeyFigure> kfs = serviceUnderTest.findAll();
		assertFalse(kfs.isEmpty());
	}

	@Override
	protected KeyFigure createNewEntity() {
	return	IntermodalRouteFigureData.getSingle();
	}

	@Override
	protected void changeEntity(KeyFigure entity) {
		entity.setTransportMode(randomAlphabetic(4));
		
	}

	@Override
	protected KeyFigure stubDaoGetOne(long id) {
	      final KeyFigure kf= kfList.get(0);
	      kf.setId(IDUtil.randomPositiveLong());
	      when(daoMock.findById(id)).thenReturn(Optional.of(kf));
	      return kf;
	}

	@Override
	protected IntermodalRouteService getApi() {
		return serviceUnderTest;
	}

	@Override
	protected IntermodalRouteRepository getDAO() {
	return daoMock;
	}

}
