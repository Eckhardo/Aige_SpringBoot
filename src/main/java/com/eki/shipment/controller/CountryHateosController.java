package com.eki.shipment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eki.shipment.model.Country;
import com.eki.shipment.service.CountryService;
import com.eki.shipment.service.GeoScopeService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@EnableJpaAuditing
@RequestMapping(value = "/countries")
public class CountryHateosController {
	Logger logger = LoggerFactory.getLogger(CountryHateosController.class);

	private CountryService countryService;
	//curl -X GET "localhost:8086/nre/country/find?country_code=DE"
	@GetMapping(produces = { "application/hal+json" })
	public Iterable<Country> findAll() {
		return countryService.findAll();
	}

	@GetMapping("/{countryId}")
	public Country getCountryById(@PathVariable String countryId) {
		return countryService.findCountry(countryId);
	}

}
