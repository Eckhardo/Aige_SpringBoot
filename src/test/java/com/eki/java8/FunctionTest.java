package com.eki.java8;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.shipment.model.GeoScope;
import com.google.common.collect.ImmutableMap;

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

	static Map<Integer, String> MY_MAP = ImmutableMap.of(1, "one", 2, "two", 3, "three", 4, "four");

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
		assertTrue(s.equals("Hamburg, Bremerhaven, Antwerp, Rotterdam"));

	}
	@Test
	public void streamWithFlatMap() {
		Map<String, List<String>> people = new HashMap<>();
		people.put("John", Arrays.asList("555-1123", "555-3389"));
		people.put("Mary", Arrays.asList("555-2243", "555-5264"));
		people.put("Steve", Arrays.asList("555-6654", "555-3242"));
		 
		List<String> phones = people.values().stream()
		  .flatMap(Collection::stream)
		    .collect(Collectors.toList());
		
		phones.forEach(System.out:: println);
	}
	@Test
	public void streamWithMap() {
		Map<String, List<String>> people = new HashMap<>();
		people.put("John", Arrays.asList("1123", "1389"));
		people.put("Mary", Arrays.asList("2434", "2264"));
		people.put("Steve", Arrays.asList("3654", "3242"));
		 
		List<String> phones = people.values().stream()
		  .map( p -> p.get(0))
		    .collect(Collectors.toList());
		
		assertThat(phones, hasItems("1123","2434","3654"));
	}


	@Test
	public void testMapComputeIfAbsent() {

		Map<Integer, String> nameMap = new HashMap<>();
		// compute if absent
		String value = nameMap.computeIfAbsent(1, v -> "John");
	assertTrue(value.equals("John"));
		// compute
	value =	nameMap.compute(1, (k, v) -> v == null ? v : v + " Lennon");
	assertTrue(value.equals("John Lennon"));



	}

	@Test
	public void testIterateMap() {
   // map.forEach
		MY_MAP.forEach((k, v) -> System.out.println((k + ":" + v)));
		//map.entrySet.stream.forEch
		MY_MAP.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
	
	}
}
