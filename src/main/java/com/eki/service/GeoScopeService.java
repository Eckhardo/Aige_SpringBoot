package com.eki.service;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.eki.country.CountryRepository;
import com.eki.geoscope.GeoScopeRepository;
import com.eki.model.Country;
import com.eki.model.GeoScope;
import com.google.common.collect.Lists;

@Service
public class GeoScopeService {
	private static final Logger logger = LoggerFactory.getLogger(GeoScopeService.class);

	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private GeoScopeRepository geoScopeRepository;

	public GeoScope save(GeoScope geoScope) {
		return geoScopeRepository.save(geoScope);
	}

	public Iterable<GeoScope> save(List<GeoScope> geoScopes) {
		return geoScopeRepository.saveAll(geoScopes);
	}

	public Iterable<GeoScope> list() {
		return geoScopeRepository.findAll();
	}

	/**
	 * 
	 * @param locationCode
	 * @param geoScopeType
	 * @param countryCode
	 * @return
	 */
	public List<GeoScope> searchGeoScopes(String locationCode, String geoScopeType, String countryCode) {
		// Define some filters
		Predicate<GeoScope> locationCodeFilter = (GeoScope gs) -> gs.getLocationCode().startsWith(locationCode);
		Predicate<GeoScope> geoScopeTypeFilter = (GeoScope gs) -> gs.getGeoScopeType().equals(geoScopeType);
		Predicate<GeoScope> countryCodeFilter = (GeoScope gs) -> StringUtils.isEmpty(countryCode) ? true
				: gs.getCountryCode().equals(countryCode);

		List<GeoScope> result = geoScopeRepository.findAll();

		return result.stream().filter(locationCodeFilter).filter(geoScopeTypeFilter).filter(countryCodeFilter)
				.collect(toList());

	}

	public List<String> mapGeoScopesToPorts(List<GeoScope> preferredGeoScopes) {
		return preferredGeoScopes.stream().map((GeoScope gs) -> gs.getLocationCode()).collect(toList());
	}

	/**
	 * Dirty Hack: Perfrerred ports are retrieved based on their country code.
	 * 
	 * @param locationCode
	 * @param geoScopeType
	 * @param countryCode
	 * @return
	 */
	public List<GeoScope> findPreferredGeoScopes(final String locationCode, final String countryCode) {

		logger.warn("code: {}, type: {}, country:{}", locationCode, countryCode);

		GeoScope geoScopeExample = new GeoScope();
		geoScopeExample.setGeoScopeType("L");
		String country = countryCode;
		if (country.isEmpty()) {
			country = locationCode.substring(0, 2);
		}
		geoScopeExample.setPort(true);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id", "countryCode", "locationCode")
				.withMatcher("geoScopeType", matcher -> matcher.exact());
		Example<GeoScope> example = Example.of(geoScopeExample, exampleMatcher);

		List<GeoScope> geoScopeList = geoScopeRepository.findAll(example);

		return filterByCountryCode(geoScopeList, country);

	}

	private List<GeoScope> filterByCountryCode(final List<GeoScope> geoScopeList, final String countryCode) {

		Predicate<GeoScope> brazilFilter = (GeoScope gs) -> gs.getCountryCode().equals("BR");

		Predicate<GeoScope> europeFiler = (GeoScope gs) -> gs.getCountryCode().equals("DE")
				|| gs.getCountryCode().equals("NL") || gs.getCountryCode().equals("BE");

		Predicate<GeoScope> selectedFilter = countryCode.equals("DE") ? europeFiler : brazilFilter;
		return geoScopeList.stream().filter(selectedFilter).collect(toList());

	}
	// ---------------------- country----------------------------

	/**
	 * 
	 * @return
	 */
	public Collection<Country> findAllCountries() {
		return countryRepository.findAll();
	}

	/**
	 * 
	 * @param searchTerm
	 * @return a collection of {@link Country} instances
	 */
	public Collection<Country> searchCountries(String searchTerm) {
		return countryRepository.findByCodeLike(searchTerm);
	}

}
