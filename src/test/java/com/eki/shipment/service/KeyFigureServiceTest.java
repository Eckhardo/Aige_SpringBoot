package com.eki.shipment.service;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.eki.shipment.dao.KeyFigureDynamicQueryDao;
import com.eki.shipment.dao.KeyFigureRepository;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;

import data.GeoScopeData;
import data.KeyFigureData;

@RunWith(MockitoJUnitRunner.class)
public class KeyFigureServiceTest {

	@Mock
	KeyFigureRepository dao;
	@Mock
	KeyFigureDynamicQueryDao dynamicDao;
	@InjectMocks
	KeyFigureService kfService;

	List<KeyFigure> kfList;
	List<GeoScope> gsList;
	List<GeoScope> portList;
	List<String> preferredPorts;

	@Before
	public void init() {
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
		List<KeyFigure> result = kfService.searchKeyFigures("DEDUS", "", "", preferredPorts, "", true, true, "", null);
		assertFalse(result.isEmpty());
	}

	@Test
	public void whenSearchKeyFiguresWithNonMatchingLocation_thenReturnEmptyResult() {
		when(dynamicDao.searchKeyFigures(Mockito.eq("DEDUS"), Mockito.anyString(), Mockito.anyString(),
				Mockito.eq(preferredPorts), Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(kfList);
		List<KeyFigure> result = kfService.searchKeyFigures("DEHAM", "", "", preferredPorts, "", true, true, "", null);
		assertTrue(result.isEmpty());
	}

	@Test
	public void whenFindAll_thenReturnNonEmptyResult() {
		when(dao.findAll()).thenReturn(kfList);
		List<KeyFigure> kfs = kfService.findAll();
		assertFalse(kfs.isEmpty());
	}

}
