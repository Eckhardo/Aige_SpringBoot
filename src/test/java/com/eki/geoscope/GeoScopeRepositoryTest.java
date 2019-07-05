package com.eki.geoscope;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.GeoScope;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations="classpath:application-test.properties")
public class GeoScopeRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private GeoScopeRepository repository;

	@Test
	public void whenFindByExampleWithMatcher_thenReturnGeoScopeCollection() {
		// given

		GeoScope gs = new GeoScope();
		gs.setLocationCode("DEH");
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnorePaths("id", "port", "countryCode", "geoScopeType")
				.withMatcher("locationCode", matcher -> matcher.startsWith());
		Example<GeoScope> example = Example.of(gs, exampleMatcher);
		// when
		List<GeoScope> found = repository.findAll(example);

		// then
		assertThat(found.isEmpty(), is(false));
		assertThat(found.size(), is(4));
		Predicate<GeoScope> p1 = g -> g.getLocationCode().equals("DEHAM");
		assertTrue(found.stream().anyMatch(p1));
		assertFalse(found.stream().allMatch(p1));
	}

	@Test
	public void whenFindByExampleWithMatcherForLocation_thenReturnGeoScopeCollection() {
		// given

		GeoScope gs = new GeoScope();
		gs.setGeoScopeType("L");
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnorePaths("id", "port", "countryCode", "locationCode")
				.withMatcher("geoScopeType", matcher -> matcher.exact());
		Example<GeoScope> example = Example.of(gs, exampleMatcher);
		// when
		List<GeoScope> found = repository.findAll(example);

		// then
		assertThat(found.isEmpty(), is(false));
//		assertThat(found.size(), is(18));
		Predicate<GeoScope> p1 = g -> g.getGeoScopeType().equals("L");
		assertTrue(found.stream().allMatch(p1));
	}

	@Test
	public void whenFindByExampleWithMatcherAndSorting_thenReturnGeoScopeCollection() {
		// given

		GeoScope gs = new GeoScope();
		gs.setLocationCode("DE");
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnorePaths("id", "port", "countryCode", "geoScopeType")
				.withMatcher("locationCode", matcher -> matcher.startsWith());
		Example<GeoScope> example = Example.of(gs, exampleMatcher);
		Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "locationCode"));
		// when
		List<GeoScope> found = repository.findAll(example, sort);
		found.forEach(System.out::println);

		// then
		assertThat(found.isEmpty(), is(false));
		assertThat(found.size(), is(9));
		Predicate<GeoScope> p1 = g -> g.getLocationCode().equals("DEHAM");
		assertTrue(found.stream().anyMatch(p1));
		assertFalse(found.stream().allMatch(p1));

	}

	@Test
	public void whenFindByExampleWithMatcherAndPaging_thenReturnGeoScopeCollection() {
		// given

		GeoScope gs = new GeoScope();
		gs.setLocationCode("DE");
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnorePaths("id", "port", "countryCode", "geoScopeType")
				.withMatcher("locationCode", matcher -> matcher.startsWith());
		Example<GeoScope> example = Example.of(gs, exampleMatcher);
		Pageable pageable = PageRequest.of(0, 1);
		// when
		Page<GeoScope> page = null;
		// then
		while (true) {
			page = repository.findAll(example, pageable);
			int number = page.getNumber();
			int numberOfElements = page.getNumberOfElements();
			int size = page.getSize();
			long totalElements = page.getTotalElements();
			int totalPages = page.getTotalPages();
			System.out.printf(
					"page info - page number %s, numberOfElements: %s, size: %s, "
							+ "totalElements: %s, totalPages: %s%n",
					number, numberOfElements, size, totalElements, totalPages);
			List<GeoScope> geoscopeList = page.getContent();
			geoscopeList.forEach(System.out::println);
			if (!page.hasNext()) {
				break;
			} else {
				System.out.println("has next");
			}
			pageable = page.nextPageable();
		}

	}

	@Test
	public void whenFindByExampleWithMatcherAndPaging2_thenReturnGeoScopeCollection() {
		// given

		GeoScope gs = new GeoScope();
		gs.setLocationCode("DE");
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnorePaths("id", "port", "countryCode", "geoScopeType")
				.withMatcher("locationCode", matcher -> matcher.startsWith());
		Example<GeoScope> example = Example.of(gs, exampleMatcher);
		Pageable pageable = PageRequest.of(0, 1);
		// when
		Page<GeoScope> page = repository.findAll(example, pageable);
		int number = page.getNumber();
		int numberOfElements = page.getNumberOfElements();
		int size = page.getSize();
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		System.out.printf(
				"page info - page number %s, numberOfElements: %s, size: %s, " + "totalElements: %s, totalPages: %s%n",
				number, numberOfElements, size, totalElements, totalPages);
		List<GeoScope> geoscopeList = page.getContent();
		geoscopeList.forEach(System.out::println);

	}

	@Test
	public void whenFindByGeoScopeCode_thenReturnGeoScopeCollection() {
		// given
		Predicate<GeoScope> p1 = g -> g.getLocationCode().equals("DEHAM");
		Predicate<GeoScope> p2 = g -> g.getLocationCode().equals("BRSSZ");

		// when
		Collection<GeoScope> found = repository.findByLocationCodeLike("DEH");

		// then
		assertThat(found.isEmpty(), is(false));
		assertThat(found.size(), is(4));
		assertTrue(found.stream().anyMatch(p1));
		assertFalse(found.stream().allMatch(p1));
		assertTrue(found.stream().noneMatch(p2));
	}

}
