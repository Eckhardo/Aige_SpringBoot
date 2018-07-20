package com.eki.keyfigure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;
import com.eki.service.GeoScopeService;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class KeyFigureDynamicQueryDaoTest {

	@Autowired
	private KeyFigureDynamicQueryDao keyFigureDynamicQueryDao;

	@Autowired
	private GeoScopeService geoScopeService;


	
	String inlandLocation = "DUSSELDORF";
	String countryCode = "DE";
	String geoScopeType = "T";
	String portLocation = "BEANR";
	String tpMode = "TRUCK";

	@Test
	public void givenAllPreferredPorts_whenSearchingForKeyFigures() {
		// given
		List<GeoScope> preferredGeoScopes = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
		assertThat(preferredGeoScopes.size(), is(4));
		List<String> preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);
		assertThat(preferredPorts.size(), is(4));

		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(inlandLocation, countryCode, geoScopeType,
				preferredPorts,true,true,null,PageRequest.of(0, 5));
		logPageDetails(page);

		assertThat(page.getContent().size(), is(5));
		Predicate<KeyFigure> p1 = g -> g.getFrom().getLocationCode().equals("DUSSELDORF");
		assertTrue(page.getContent().stream().allMatch(p1));
		
		page.getContent().forEach(System.out::println);
		
		while(page.hasNext()) {
			Pageable nextPage=page.nextPageable();
			 page = keyFigureDynamicQueryDao.searchPageableKeyFigures(inlandLocation, countryCode, geoScopeType,
					preferredPorts,true,true,null,PageRequest.of(nextPage.getPageNumber(), 5));
				logPageDetails(page);
				assertTrue(page.getContent().stream().allMatch(p1));


		}
	}




	

	@Test
	public void givenOnePreferredPort_whenSearchingForKeyFigures() {
		// given
	
		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(inlandLocation, countryCode, geoScopeType,
				Arrays.asList(portLocation),true,true,null, PageRequest.of(0, 5));
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
	
		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(inlandLocation, countryCode, geoScopeType,
				Arrays.asList(portLocation),true,true,tpMode,PageRequest.of(0, 5));
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
	
		Page<KeyFigure> page = keyFigureDynamicQueryDao.searchPageableKeyFigures(inlandLocation, countryCode, geoScopeType,
				Arrays.asList(portLocation),true,false,tpMode,PageRequest.of(0, 5));
		logPageDetails(page);

		assertThat(page.getContent().size(), is(1));
		Predicate<KeyFigure> p1 = kf -> kf.getTransportMode().equals("TRUCK");
		Predicate<KeyFigure> p2 = kf -> kf.getEquipmentSize().equals("20");
		assertTrue(page.getContent().stream().allMatch(p1));
		assertTrue(page.getContent().stream().allMatch(p2));
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
