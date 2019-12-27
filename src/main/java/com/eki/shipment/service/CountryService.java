package com.eki.shipment.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eki.shipment.dao.CountryRepository;
import com.eki.shipment.model.Country;

@Service
@Transactional
public class CountryService extends AbstractService<Country> {

	@Autowired
	CountryRepository dao;

	public CountryService() {
		super(Country.class);
	}

	/**
	 * 
	 * @param searchTerm
	 * @return a collection of {@link Country} instances
	 */
	public Collection<Country> searchCountries(String searchTerm) {
		return dao.findByCodeLike(searchTerm);
	}

	public Country findCountry(String countryCode) {
		return dao.findByCode(countryCode);
	}



	@Override
	protected CountryRepository getDao() {
		return dao;
	}

}
