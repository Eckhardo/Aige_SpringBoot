package com.eki.geoscope;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eki.model.GeoScope;
import com.eki.service.GeoScopeService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class GeoScopeController {

	@Autowired
	private GeoScopeService geoScopeService;

	@GetMapping({ "/geoscope/filter" })
	public List<GeoScope> searchGeoScope(@RequestParam(value = "location_code", required = true) String locationCode,
			@RequestParam(value = "geo_scope_type", required = true) String geoScopeType,
			@RequestParam(value = "country_code", required = false) String countryCode) {

		System.out.println("com.eki.globe.GeoScopesResource.searchGeoScope()");
		System.out.println("location search " + locationCode);
		System.out.println("geo scope type " + geoScopeType);
		System.out.println("country code " + countryCode);

	return geoScopeService.searchGeoScopes(locationCode, geoScopeType, countryCode);

	}

	
	@GetMapping({ "/preferredPort/filter" })
	public List<GeoScope> searchPreferredPort(@RequestParam(value = "location_code", required = true) String locationCode,
			@RequestParam(value = "geo_scope_type", required = true) String geoScopeType,
			@RequestParam(value = "country_code", required = false) String countryCode) {

		System.out.println("com.eki.globe.GeoScopesResource.searchPreferredPort()");
		System.out.println("location search " + locationCode);
		System.out.println("geo scope type " + geoScopeType);
		System.out.println("country code " + countryCode);

		return geoScopeService.findPreferredGeoScopes(locationCode,  countryCode);
	}

}
