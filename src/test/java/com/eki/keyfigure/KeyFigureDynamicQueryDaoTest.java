package com.eki.keyfigure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;
import com.eki.service.GeoScopeService;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations="classpath:application-test.properties")
public class KeyFigureDynamicQueryDaoTest {

	@Autowired
	private KeyFigureDynamicQueryDao keyFigureDynamicQueryDao;

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
		assertThat(preferredGeoScopes.size(), is(4));
		List<String> preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);
		assertThat(preferredPorts.size(), is(4));

		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(DUSSELDORF, DE, geoScopeType,
				preferredPorts,true,true,null,null,PageRequest.of(0, 5));
		logPageDetails(page);

		assertThat(page.getContent().size(), is(5));
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

		assertThat(kfs.size(), is(16));
	

	}


	@Test
	public void given40Feet_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), false, true, null, null);

		assertThat(kfs.size(), is(11));
	

	}
	@Test
	public void given20Feet_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), true, false, null, null);

		assertThat(kfs.size(), is(5));

	}
	
	@Test
	public void givenTransportModeBarge_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), true, true, "BARGE", null);

		assertThat(kfs.size(), is(2));
	

	}
	@Test
	public void givenTransportModeEmptyString_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> kfs = keyFigureDynamicQueryDao.searchKeyFigures(DUSSELDORF, DE, geoScopeType,
				new ArrayList<>(), true, true, "", null);

		assertThat(kfs.size(), is(16));
	

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
		page.getContent().forEach(System.out::println);

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
		page.getContent().forEach(System.out::println);

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
		page.getContent().forEach(System.out::println);
	

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
		page.getContent().forEach(System.out::println);
	

	}
	private void logPageDetails(Page<KeyFigure> page) {
		int number = page.getNumber();
		int numberOfElements = page.getNumberOfElements();
		int size = page.getSize();
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		System.out.printf(
				"page info - page number %s, numberOfElements: %s, size: %s, " + "totalElements: %s, totalPages: %s%n",
				number, numberOfElements, size, totalElements, totalPages);
	}

}
