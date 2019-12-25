package com.eki.play.java8;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eki.shipment.dao.AbstractRepositoryTest;
import com.eki.shipment.dao.KeyFigureDynamicQueryDao;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.service.GeoScopeService;

/**
 * Some tests for the new Java 6 {@link java.util.Comparator} including:
 * 
 * <ul>
 * <li>{@link java.util.Comparator#compare(Object, Object)}</li>
 * <li>{@link java.util.Comparator#thenComparing(java.util.function.Function)}</li>
 * <li>{@link java.util.Comparator#nullsFirst(Comparator)}</li> *
 * <li>{@link java.util.Comparator#nullsLast(Comparator)}</li>
 * </ul>
 * 
 * @author ekirschning
 *
 */

public class ComparatorTest extends AbstractRepositoryTest {
	Logger logger = LoggerFactory.getLogger(ComparatorTest.class);

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
				geoScopeType, preferredPorts, true, true, "TRUCK", null);

		assertThat(keyFiguresWithDifferentRates.size(), is(8));

		keyFiguresWithEqualRates = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType,
				preferredPorts, true, true, null, null);

		assertThat(keyFiguresWithEqualRates.size(), is(16));

	}

	@Test
	public void compareWithComparator() {
		// given
		assertThat(keyFiguresWithDifferentRates.size(), is(8));

		// test
		Comparator<KeyFigure> rateComparator = (kf1, kf2) -> (kf1.getRate().compareTo(kf2.getRate()));

		keyFiguresWithDifferentRates.sort(rateComparator);

		assertCorrectSorting(keyFiguresWithDifferentRates);
	}

	@Test
	public void compareWithComparing() {
		// given
		assertThat(keyFiguresWithDifferentRates.size(), is(8));

		// test
		Comparator<KeyFigure> rateComparator = Comparator.comparing((KeyFigure kf) -> kf.getRate());

		keyFiguresWithDifferentRates.sort(rateComparator);

		assertCorrectSorting(keyFiguresWithDifferentRates);

	}

	@Test
	public void compareWithComparingNullsFirst() {
		// given
		assertThat(keyFiguresWithEqualRates.size(), is(16));

		// test
		Comparator<KeyFigure> rateComparator = Comparator.comparing((KeyFigure kf) -> kf.getTransportMode());

		keyFiguresWithDifferentRates.sort(rateComparator);

	
	}

	@Test
	public void compareWithComparingNullsLast() {
		// given
		assertThat(keyFiguresWithEqualRates.size(), is(16));

		// test
		Comparator<KeyFigure> rateComparator = Comparator.comparing(KeyFigure::getRate,
				Comparator.nullsLast(BigDecimal::compareTo));

		keyFiguresWithEqualRates.sort(rateComparator);

	}

	/**
	 * sorted values are either ascending or arre equal.
	 * 
	 * @param kfs A collection of key figures.
	 */
	private void assertCorrectSorting(List<KeyFigure> kfs) {
		KeyFigure latestKf = null;
		for (KeyFigure keyFigure : kfs) {
			if (latestKf != null) {
				assertThat(keyFigure.getRate().compareTo(latestKf.getRate()), not(-1));
			}
			latestKf = keyFigure;

		}
	}

}
