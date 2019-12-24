package com.eki.shipment.service;

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
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.eki.shipment.dao.CountryRepository;
import com.eki.shipment.dao.GeoScopeRepository;
import com.eki.shipment.model.Country;
import com.eki.shipment.model.GeoScope;

@Service
@Transactional
public class GeoScopeService extends AbstractService<GeoScope> {

	private static final Logger logger = LoggerFactory.getLogger(GeoScopeService.class);

	public GeoScopeService() {
		super(GeoScope.class);
	}

	@Autowired
	private GeoScopeRepository dao;



	@Override
	protected GeoScopeRepository getDao() {
		return dao;
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

		List<GeoScope> result = dao.findAll();

		return result.stream().filter(locationCodeFilter).filter(geoScopeTypeFilter).filter(countryCodeFilter)
				.collect(toList());

	}

	public Collection<GeoScope> findPorts(String searchTerm) {

		return dao.findPortsLike(searchTerm);
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
			GeoScope nonLocation = dao.findByLocationCode(locationCode);
			country = nonLocation.getCountryCode();
		}
		geoScopeExample.setPort(true);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id", "countryCode", "locationCode")
				.withMatcher("geoScopeType", matcher -> matcher.exact());
		Example<GeoScope> example = Example.of(geoScopeExample, exampleMatcher);

		List<GeoScope> geoScopeList = dao.findAll(example);
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

	public GeoScope findByLocationName(String locationName) {
		return dao.findByName(locationName);
	}
	// ---------------------- country----------------------------


}
