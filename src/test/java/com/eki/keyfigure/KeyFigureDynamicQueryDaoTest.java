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

		List<KeyFigure> results = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType,
				preferredPorts,true,true,null);
		assertThat(results.size(), is(16));
		Predicate<KeyFigure> p1 = g -> g.getFrom().getLocationCode().equals("DUSSELDORF");
	
		assertTrue(results.stream().allMatch(p1));
		
		results.forEach(System.out::println);

	}

	

	@Test
	public void givenOnePreferredPort_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> results = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType,
				Arrays.asList(portLocation),true,true, null);
		assertThat(results.size(), is(4));
		Predicate<KeyFigure> p1 = kf -> kf.getFrom().getLocationCode().equals("DUSSELDORF");
		Predicate<KeyFigure> p2 = kf -> kf.getTo().getLocationCode().equals("BEANR");
		assertTrue(results.stream().allMatch(p1));
		assertTrue(results.stream().allMatch(p2));
		results.forEach(System.out::println);

	}
	

	@Test
	public void givenTransportModeAndEquipmentInfo_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> results = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType,
				Arrays.asList(portLocation),true,true,tpMode);
		assertThat(results.size(), is(2));
		Predicate<KeyFigure> p1 = kf -> kf.getTransportMode().equals("TRUCK");
		Predicate<KeyFigure> p2 = kf -> kf.getTo().getLocationCode().equals("BEANR");
	
		assertTrue(results.stream().allMatch(p1));
		assertTrue(results.stream().allMatch(p2));
		results.forEach(System.out::println);

	}


	@Test
	public void givenTransportModeAnd20Feet_whenSearchingForKeyFigures() {
		// given
	
		List<KeyFigure> results = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType,
				Arrays.asList(portLocation),true,false,tpMode);
		assertThat(results.size(), is(1));
		Predicate<KeyFigure> p1 = kf -> kf.getTransportMode().equals("TRUCK");
		Predicate<KeyFigure> p2 = kf -> kf.getEquipmentSize().equals("20");
		assertTrue(results.stream().allMatch(p1));
		assertTrue(results.stream().allMatch(p2));
		results.forEach(System.out::println);
	

	}

}
