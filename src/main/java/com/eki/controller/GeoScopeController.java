package com.eki.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eki.model.GeoScope;
import com.eki.service.GeoScopeService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders="*")
@RestController
public class GeoScopeController {
	Logger logger = LoggerFactory.getLogger(GeoScopeController.class);
	 

	@Autowired
	private GeoScopeService geoScopeService;

	@GetMapping({ "/geoscope/filter" })
	public List<GeoScope> searchGeoScope( @RequestParam(value = "location_code", required = true) String locationCode,
			@RequestParam(value = "geo_scope_type", required = true) String geoScopeType,
			@RequestParam(value = "country_code", required = false) String countryCode) {
 		return geoScopeService.searchGeoScopes(locationCode, geoScopeType, countryCode);

	}

	
	@GetMapping({ "/preferredPort/filter" })
	public List<GeoScope> searchPreferredPort(@RequestParam(value = "location_code", required = true) String locationCode,
			@RequestParam(value = "geo_scope_type", required = true) String geoScopeType,
			@RequestParam(value = "country_code", required = false) String countryCode) {


		return geoScopeService.findPreferredGeoScopes(locationCode,  countryCode);
	}
	
	
	@GetMapping({ "/ports/filter" })
	public Collection<GeoScope> searchPorts(@RequestParam(value = "location_code", required = true) String locationCode) {


		return geoScopeService.findPorts(locationCode);
	
	}

}
