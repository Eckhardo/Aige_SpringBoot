package com.eki.play.java8;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.util.EntityFactory;
import com.google.common.collect.Ordering;

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

public class ComparatorTest {
	Logger logger = LoggerFactory.getLogger(ComparatorTest.class);

	List<GeoScope> preferredGeoScopes;


	@Before
	public void init() {
	 preferredGeoScopes = EntityFactory.createGeoScopes();
		assertThat(preferredGeoScopes.size(), is(4));
	

	}

	@Test
	public void compareWithComparator() {
		// given
		assertThat(preferredGeoScopes.size(), is(4));

		// test
		Comparator<GeoScope> rateComparator = (g1, g2) -> (g1.getName().compareTo(g2.getName()));
		 Comparator<GeoScope> comparatorObj=Comparator.comparing( g -> g.getCountryCode());
		preferredGeoScopes.sort(rateComparator);
           Collections.sort(preferredGeoScopes, comparatorObj);
		assertCorrectSorting(preferredGeoScopes);
	}

	@Test
	public void compareWithComparing() {
		// given
		assertThat(preferredGeoScopes.size(), is(4));

		// test
		// test
		Comparator<GeoScope> rateComparator = Comparator.comparing( g-> g.getLocationCode());

		preferredGeoScopes.sort(rateComparator);

		assertCorrectSorting(preferredGeoScopes);

	}

	@Test
	public void compareWithComparingNullsFirst() {
		// given
		assertThat(preferredGeoScopes.size(), is(4));
		// test
		Comparator<GeoScope> rateComparator = (gs1, gs2) -> (gs1.getName().compareTo(gs2.getName()));

		preferredGeoScopes.sort(rateComparator);

	
	}
	public static boolean isSorted(List<Long> listOfStrings) {
	    return Ordering.<Long> natural().isOrdered(listOfStrings);
	}
	@Test
	public void compareWithComparingNullsLast() {
		// given
		assertThat(preferredGeoScopes.size(), is(4));

		// test
		Comparator<GeoScope> rateComparator = Comparator.comparing(GeoScope::getCountryCode,
				Comparator.nullsLast(String::compareTo));

		preferredGeoScopes.sort(rateComparator);

	}

	/**
	 * sorted values are either ascending or arre equal.
	 * 
	 * @param gs A collection of key figures.
	 */
	private void assertCorrectSorting(List<GeoScope> gs) {
		GeoScope latestGs = null;
		for (GeoScope g : gs) {
			if (latestGs != null) {
				assertThat(g.getName().compareTo(latestGs.getName()), not(-1));
			}
			latestGs = g;

		}
	}

}
