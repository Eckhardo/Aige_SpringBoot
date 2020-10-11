package com.eki.shipment.dao;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.run.MysqlBootApplication;
import com.eki.shipment.service.GeoScopeService;
@SpringBootTest(classes = MysqlBootApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations="classpath:application-test.properties")
public class IntermodalRouteDynamicQueryDaoTest {
	Logger logger = LoggerFactory.getLogger(IntermodalRouteDynamicQueryDaoTest.class);

	@Autowired
	private IntermodalRouteDynamicQueryDao keyFigureDynamicQueryDao;

	@Autowired
	private GeoScopeService geoScopeService;
	
	static String DUSSELDORF = "DUSSELDORF";
	static String DE = "DE";
	String geoScopeType = "T";
	String portLocation = "BEANR";
	String tpMode = "TRUCK";
	String eqGroup ="GP";

	@Test
	public void givenAllPreferredPorts_whenSearchingForKeyFigures() {
		// given
		List<GeoScope> preferredGeoScopes = geoScopeService.findPreferredGeoScopes(DUSSELDORF, DE);
	
		List<String> preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);
	

		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(DUSSELDORF, DE, geoScopeType,
				preferredPorts,true,true,null,null,PageRequest.of(0, 5));
		logPageDetails(page);

		assertThat(page.getContent(), is(not((empty()))));
		Predicate<KeyFigure> p1 = g -> g.getFrom().getLocationCode().equals("DUSSELDORF");
		assertTrue(page.getContent().stream().allMatch(p1));
		
		page.getContent().forEach(System.out::println);
		
		while(page.hasNext()) {
			Pageable nextPage=page.nextPageable();
			 page = keyFigureDynamicQueryDao.searchPageableKeyFigures(DUSSELDORF, DE, geoScopeType,
					preferredPorts,true,true,null,null,PageRequest.of(nextPage.getPageNumber(), 5));
				logPageDetails(page);
				assertTrue(page.getContent().stream().allMatch(p1));


		}
	}


	@Test
	public void given20And40Feet_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), true, true, null, null);

		assertThat(kfs, is(not((empty()))));
	

	}


	@Test
	public void given40Feet_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), false, true, null, null);

		assertThat(kfs, is(not((empty()))));
	

	}
	@Test
	public void given20Feet_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), true, false, null, null);

		assertThat(kfs, is(not((empty()))));
	}
	
	@Test
	public void givenTransportModeBarge_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), true, true, "BARGE", null);

		assertThat(kfs, is(not((empty()))));
	

	}
	@Test
	public void givenTransportModeEmptyString_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), true, true, "", null);

		assertThat(kfs, is(not((empty()))));
	

	}

	@Test
	public void givenOnePreferredPort_whenSearchingForKeyFigures() {
		// given
	
		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(DUSSELDORF, DE, geoScopeType,
				Arrays.asList(portLocation),true,true,null, null,PageRequest.of(0, 5));
		logPageDetails(page);

		assertThat(page.getContent().size(), is(4));
		Predicate<KeyFigure> p1 = kf -> kf.getFrom().getLocationCode().equals("DUSSELDORF");
		Predicate<KeyFigure> p2 = kf -> kf.getTo().getLocationCode().equals("BEANR");
		assertTrue(page.getContent().stream().allMatch(p1));
		assertTrue(page.getContent().stream().allMatch(p2));
		logResult(page);

	}


	private void logResult(Page<KeyFigure> page) {
		List<String> names =page.stream().map(kf-> kf.getFrom().getName()).collect(Collectors.toList());
		names.forEach(logger::trace);
	}
	

	@Test
	public void givenTransportModeAndEquipmentInfo_whenSearchingForKeyFigures() {
		// given
	
		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(DUSSELDORF, DE, geoScopeType,
				Arrays.asList(portLocation),true,true,tpMode,null,PageRequest.of(0, 5));
		logPageDetails(page);

		assertThat(page.getContent().size(), is(2));
		Predicate<KeyFigure> p1 = kf -> kf.getTransportMode().equals("TRUCK");
		Predicate<KeyFigure> p2 = kf -> kf.getTo().getLocationCode().equals("BEANR");
	
		assertTrue(page.getContent().stream().allMatch(p1));
		assertTrue(page.getContent().stream().allMatch(p2));
		logResult(page);

	}


	@Test
	public void givenTransportModeAnd20Feet_whenSearchingForKeyFigures() {
		// given
	
		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(DUSSELDORF, DE, geoScopeType,
				Arrays.asList(portLocation),true,false,tpMode,null,PageRequest.of(0, 5));
		logPageDetails(page);

		assertThat(page.getContent().size(), is(1));
		Predicate<KeyFigure> p1 = kf -> kf.getTransportMode().equals("TRUCK");
		Predicate<KeyFigure> p2 = kf -> kf.getEquipmentSize().equals("20");
		assertTrue(page.getContent().stream().allMatch(p1));
		assertTrue(page.getContent().stream().allMatch(p2));
		logResult(page);

	

	}
	@Test
	public void givenEquipmentGroup_whenSearchingForKeyFigures() {
		// given
	
		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(DUSSELDORF, DE, geoScopeType,
				Arrays.asList(portLocation),true,true,null,eqGroup,PageRequest.of(0, 5));
		logPageDetails(page);

		assertThat(page.getContent().size(), is(4));
		Predicate<KeyFigure> p1 = kf -> kf.getEquipmentGroup().equals("GP");
		assertTrue(page.getContent().stream().allMatch(p1));
		logResult(page);

	}
	@Test
	public void searchKeyFiguresByJpa() {
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFiguresJpa(DUSSELDORF, DE, geoScopeType,
				Arrays.asList("DEHAM"));

		assertThat(kfs, is(not((empty()))));
	

	}
	@Test
	public void searchKeyFiguresByNamedJpa() {
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFiguresNamedJpa(DUSSELDORF, 
				Arrays.asList("DEHAM"));

		assertThat(kfs, is(not((empty()))));
	

	}

	private void logPageDetails(Page<KeyFigure> page) {
		int number = page.getNumber();
		int numberOfElements = page.getNumberOfElements();
		int size = page.getSize();
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		logger.trace(
				"page info - page number %s, numberOfElements: %s, size: %s, " + "totalElements: %s, totalPages: %s%n",
				number, numberOfElements, size, totalElements, totalPages);
	}



}
