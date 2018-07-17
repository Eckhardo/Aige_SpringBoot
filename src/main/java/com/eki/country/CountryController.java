package com.eki.country;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eki.model.Country;
import com.eki.service.GeoScopeService;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@EnableJpaAuditing
public class CountryController {

	@Autowired
private GeoScopeService geoScopeService;	
	
	  @GetMapping("/country/filter")
	  public Collection<Country> searchByCode(@RequestParam(value = "country_code", required = true) String countryCode) {
	      return geoScopeService.searchCountries(countryCode);
	  }

	  

	  @GetMapping("/country/find")
	  public Country findByCode(@RequestParam(value = "country_code", required = true) String countryCode) {
	      return geoScopeService.findCountry(countryCode);
	  }
	
}