package com.eki.java8;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.GeoScope;


/**
 * 
 * @author ekirschning
 *
 */
@RunWith(SpringRunner.class)
public class FunctionTest {

	static List<GeoScope> geoScopeList = new ArrayList<>(
			Arrays.asList(new GeoScope(1, "DE", "DEHAM", "L", "Hamburg", true),
					new GeoScope(1, "DE", "DEBRV", "L", "Bremerhaven", true),
					new GeoScope(1, "DE", "DEBER", "L", "Berlin", false),
					new GeoScope(1, "DE", "DEHAJ", "L", "Hannover", false),
					new GeoScope(1, "BE", "BEANR", "L", "Antwerp", true),
					new GeoScope(1, "NL", "NLRTM", "L", "Rotterdam", true)));

	@Test
	public void testPredicateRemoveIf() {

		Predicate<GeoScope> isPort = GeoScope::isPort;
		geoScopeList.removeIf(isPort);
		assertThat(geoScopeList, hasSize(2));
	}

	@Test
	public void testPredicateStreamFilter() {

		Predicate<GeoScope> isPort = GeoScope::isPort;
		List<GeoScope> filteredList = geoScopeList.stream().filter(isPort).collect(toList());
		assertThat(filteredList, hasSize(4));
	}

	@Test
	public void testFunctionStreamMap() {

		Function<GeoScope, String> getCountryCode = GeoScope::getCountryCode;
		List<String> listOfStrings = geoScopeList.stream().map(getCountryCode).collect(toList());
		assertThat(listOfStrings, hasItems("DE", "BE", "NL"));

	}

	@Test
	public void testStreamFilterMapReduce() {
		String s = geoScopeList.stream().filter(b -> b.isPort()).map(b -> b.getLocationName()).reduce("",
				(s1, s2) -> (s1.isEmpty()) ? s2 : s1 + ", " + s2);
		System.out.println(s);

	}
}
