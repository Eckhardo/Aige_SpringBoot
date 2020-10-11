package com.eki.shipment.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.util.EntityFactory;

public class GeoScopeRepositoryTest extends AbstractRepositoryTest<GeoScope,Long>{
	Logger logger = LoggerFactory.getLogger(GeoScopeRepositoryTest.class);

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
		List<String> names =found.stream().map(g-> g.getName()).collect(Collectors.toList());
		names.forEach(logger::trace);
		// then
		assertThat(found.isEmpty(), is(false));
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
			logger.trace(
					"page info - page number %s, numberOfElements: %s, size: %s, "
							+ "totalElements: %s, totalPages: %s%n",
					number, numberOfElements, size, totalElements, totalPages);
			List<GeoScope> geoscopeList = page.getContent();
			List<String> names =geoscopeList.stream().map(g-> g.getName()).collect(Collectors.toList());
			names.forEach(logger::trace);
			if (!page.hasNext()) {
				break;
			} else {
				logger.trace("has next");
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
		logger.trace(
				"page info - page number %s, numberOfElements: %s, size: %s, " + "totalElements: %s, totalPages: %s%n",
				number, numberOfElements, size, totalElements, totalPages);
		List<GeoScope> geoscopeList = page.getContent();
		List<String> names =geoscopeList.stream().map(g-> g.getName()).collect(Collectors.toList());
		names.forEach(logger::trace);

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
			assertTrue(found.stream().anyMatch(p1));
		assertFalse(found.stream().allMatch(p1));
		assertTrue(found.stream().noneMatch(p2));
	}

	@Test
	public void whenFindPorts_thenReturnGeoScopeCollection() {
		// given
		Predicate<GeoScope> p1 = g -> g.getLocationCode().equals("DEHAM");
		Predicate<GeoScope> p2 = g -> g.getLocationCode().equals("BRSSZ");

		// when
		Collection<GeoScope> found = repository.findPortsLike("DE");

		// then
		assertThat(found.isEmpty(), is(false));
		assertTrue(found.stream().anyMatch(p1));
		assertFalse(found.stream().allMatch(p1));
		assertTrue(found.stream().noneMatch(p2));
	}

	@Override
	protected GeoScope createNewEntity() {
	return EntityFactory.createGeoScope();
	}

	@Override
	protected GeoScopeRepository getApi() {
		return repository;
	}
}
