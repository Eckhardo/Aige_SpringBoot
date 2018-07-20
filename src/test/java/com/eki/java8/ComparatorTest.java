package com.eki.java8;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
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

import com.eki.keyfigure.KeyFigureDynamicQueryDao;
import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;
import com.eki.service.GeoScopeService;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ComparatorTest {

	@Autowired
	private KeyFigureDynamicQueryDao keyFigureDynamicQueryDao;

	@Autowired
	private GeoScopeService geoScopeService;

	List<KeyFigure> keyFiguresWithDifferentRates;

	List<KeyFigure> keyFiguresWithEqualRates;

	private final String inlandLocation = "DUSSELDORF";
	private String countryCode = "DE";
	private String geoScopeType = "T";

	@Before
	public void init() {
		List<GeoScope> preferredGeoScopes = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
		assertThat(preferredGeoScopes.size(), is(4));
		List<String> preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);
		assertThat(preferredPorts.size(), is(4));
		keyFiguresWithDifferentRates = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode,
				geoScopeType, preferredPorts, true, true, "TRUCK");

		assertThat(keyFiguresWithDifferentRates.size(), is(8));
		
		keyFiguresWithEqualRates = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode,
				geoScopeType, preferredPorts, true, true, null);

		assertThat(keyFiguresWithEqualRates.size(), is(16));

	}

	@Test
	public void compareWithComparator() {
		// given
		assertThat(keyFiguresWithDifferentRates.size(), is(8));

		// test
		Comparator<KeyFigure> rateComparator = (KeyFigure kf1, KeyFigure kf2) -> {
			return (kf1.getRate().compareTo(kf2.getRate()));
		};

		Collections.sort(keyFiguresWithDifferentRates, rateComparator);

		assertCorrectSorting(keyFiguresWithDifferentRates);
	}

	@Test
	public void compareWithComparing() {
		// given
		assertThat(keyFiguresWithDifferentRates.size(), is(8));

		// test
		Comparator<KeyFigure> rateComparator = Comparator.comparing((KeyFigure kf) -> kf.getRate());

		Collections.sort(keyFiguresWithDifferentRates, rateComparator);

		assertCorrectSorting(keyFiguresWithDifferentRates);
	}

	@Test
	public void compareWithComparingNullsFirst() {
		// given
		assertThat(keyFiguresWithEqualRates.size(), is(16));

		// test
		Comparator<KeyFigure> rateComparator = Comparator.comparing(KeyFigure::getTransportMode, Comparator.nullsFirst(String::compareTo));

		Collections.sort(keyFiguresWithEqualRates, rateComparator);

		assertCorrectSortingPreferred(keyFiguresWithEqualRates);
	}
	
	@Test
	public void compareWithComparingNullsLast() {
		// given
		assertThat(keyFiguresWithEqualRates.size(), is(16));

		// test
		Comparator<KeyFigure> rateComparator = Comparator.comparing(KeyFigure::getTransportMode, Comparator.nullsLast(String::compareTo));

		Collections.sort(keyFiguresWithEqualRates, rateComparator);

		assertCorrectSortingPreferred(keyFiguresWithEqualRates);
	}
	private void assertCorrectSortingPreferred(List<KeyFigure> kfs) {
	
		for (KeyFigure keyFigure : kfs) {
			System.out.println(keyFigure.getTransportMode());
		}
	}
	
	private void assertCorrectSorting(List<KeyFigure> kfs) {
		KeyFigure latestKf = null;
		for (KeyFigure keyFigure : kfs) {
			if (latestKf != null) {
				assertThat(keyFigure.getRate().compareTo(latestKf.getRate()), is(1));
			}
			latestKf = keyFigure;
			System.out.println(keyFigure.getRate());
		}
	}

}
