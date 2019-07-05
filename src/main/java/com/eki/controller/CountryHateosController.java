package com.eki.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eki.model.Country;
import com.eki.service.GeoScopeService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@EnableJpaAuditing
@RequestMapping(value = "/countries")
public class CountryHateosController {
	Logger logger = LoggerFactory.getLogger(CountryHateosController.class);

	@Autowired
	private GeoScopeService geoScopeService;
//curl -X GET "localhost:8086/nre/country/find?country_code=DE"
	@GetMapping(produces = { "application/hal+json" })
	public Iterable<Country> findAll() {
		return geoScopeService.findAll();
	}


	  @GetMapping("/{countryId}")
	    public Country getCountryById(@PathVariable String countryId) {
	        return geoScopeService.findCountry(countryId);
	    }

}
