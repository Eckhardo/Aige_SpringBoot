package com.eki.shipment.service;

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

import com.eki.shipment.dao.KeyFigureDynamicQueryDao;
import com.eki.shipment.dao.KeyFigureRepository;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.util.IDUtil;

import data.GeoScopeData;
import data.KeyFigureData;


public class KeyFigureServiceUnitTest extends AbstractServiceUnitTest<KeyFigure>{

	@Mock
	KeyFigureRepository daoMock;
	@Mock
	KeyFigureDynamicQueryDao dynamicDao;
	@InjectMocks
	KeyFigureService serviceUnderTest;

	List<KeyFigure> kfList;
	List<GeoScope> gsList;
	List<GeoScope> portList;
	List<String> preferredPorts;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		kfList = KeyFigureData.getData();
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
	return	KeyFigureData.getData().get(0);
	}

	@Override
	protected void changeEntity(KeyFigure entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected KeyFigure stubDaoGetOne(long id) {
	      final KeyFigure kf= kfList.get(0);
	      kf.setId(IDUtil.randomPositiveLong());
	      when(daoMock.findById(id)).thenReturn(Optional.of(kf));
	      return kf;
	}

	@Override
	protected KeyFigureService getApi() {
		return serviceUnderTest;
	}

	@Override
	protected KeyFigureRepository getDAO() {
	return daoMock;
	}

}
