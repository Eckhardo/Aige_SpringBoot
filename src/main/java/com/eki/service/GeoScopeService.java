package com.eki.service;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.eki.country.CountryRepository;
import com.eki.geoscope.GeoScopeRepository;
import com.eki.model.Country;
import com.eki.model.GeoScope;

@Service
public class GeoScopeService {
	private static final Logger logger = LoggerFactory.getLogger(GeoScopeService.class);

	private CountryRepository countryRepository;

	private GeoScopeRepository geoScopeRepository;

	@Autowired
	public void setCountryRepository(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Autowired
	public void setGeoScopeRepository(GeoScopeRepository geoScopeRepository) {
		this.geoScopeRepository = geoScopeRepository;
	}

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

	public Collection<GeoScope> findPorts(String searchTerm) {

		return geoScopeRepository.findPortsLike(searchTerm);
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

		GeoScope geoScopeExample = new GeoScope();
		geoScopeExample.setGeoScopeType("L");
		String country = countryCode;
		if (country.isEmpty() && locationCode.length() == 5) {
			country = locationCode.substring(0, 2);
		} else if (country.isEmpty() && locationCode.length() != 5) {
			GeoScope nonLocation = geoScopeRepository.findByLocationCode(locationCode);
			country = nonLocation.getCountryCode();
		}
		geoScopeExample.setPort(true);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id", "countryCode", "locationCode")
				.withMatcher("geoScopeType", matcher -> matcher.exact());
		Example<GeoScope> example = Example.of(geoScopeExample, exampleMatcher);

		List<GeoScope> geoScopeList = geoScopeRepository.findAll(example);
		logger.debug("# preferredGeoScopes: {}", geoScopeList.size());

		return filterByCountryCode(geoScopeList, country);

	}

	private List<GeoScope> filterByCountryCode(final List<GeoScope> geoScopeList, final String countryCode) {

		Predicate<GeoScope> brazilFilter = (GeoScope gs) -> gs.getCountryCode().equals("BR");

		Predicate<GeoScope> europeFiler = (GeoScope gs) -> gs.getCountryCode().equals("DE")
				|| gs.getCountryCode().equals("NL") || gs.getCountryCode().equals("BE");

		Predicate<GeoScope> selectedFilter = countryCode.equals("DE") ? europeFiler : brazilFilter;
		return geoScopeList.stream().filter(selectedFilter).collect(toList());

	}

	public Optional<GeoScope> findOne(long id) {

		return geoScopeRepository.findById(id);
	}

	public Optional<GeoScope> findByLocationName(String locationName) {
		return geoScopeRepository.findByLocationName(locationName);
	}
	// ---------------------- country----------------------------

	/**
	 * 
	 * @param searchTerm
	 * @return a collection of {@link Country} instances
	 */
	public Collection<Country> searchCountries(String searchTerm) {
		return countryRepository.findByCodeLike(searchTerm);
	}

	public Country findCountry(String countryCode) {
		return countryRepository.findByCode(countryCode);
	}

	public Iterable<Country> findAll() {
		return countryRepository.findAll();
	}

}
