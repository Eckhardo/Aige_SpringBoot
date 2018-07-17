package com.eki.keyfigure;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eki.model.KeyFigure;
import com.eki.model.RESTDateParam;
import com.eki.model.SearchCriteria;
import com.eki.service.KeyFigureService;
import com.google.common.base.Strings;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class KeyFigureController {

	private static final Logger logger = LoggerFactory.getLogger(KeyFigureController.class);

	@Autowired
	private KeyFigureService kfService;

	@GetMapping({ "/keyfigure/filter" })
	public List<KeyFigure> searchGeoScope(
			@RequestParam(value = "includeImTariff", defaultValue = "false") boolean includeIt,
			@RequestParam(value = "includeImSchedule", defaultValue = "false") boolean includeIs,
			@RequestParam(value = "isPreCarriage", defaultValue = "true") boolean isPreCarriage,
			@RequestParam(value = "inlandLocation") String inlandLocation,
			@RequestParam(value = "inlandGeoScopeType") String inlandGeoScopeType,
			@RequestParam(value = "countryCode") String countryCode,
			@RequestParam(value = "portLocation") String portLocation,
			@RequestParam(value = "includeAllPrefPorts", defaultValue = "true") boolean includeAllPrefPorts,
			@RequestParam(value = "transportMode") String transportMode,
			@RequestParam(value = "equipmentType") String equipmentType, @RequestParam(value = "eq20") boolean eq20,
			@RequestParam(value = "eq40") boolean eq40, @RequestParam(value = "eqHC") boolean eqHC,
			@RequestParam(value = "weight20") String weight20, @RequestParam(value = "weight40") String weight40,
			@RequestParam(value = "weightBasedOnly", defaultValue = "false") boolean weightBasedOnly,
			@RequestParam(value = "startDate") RESTDateParam startDate,
			@RequestParam(value = "endDate") RESTDateParam endDate) {

		System.out.println("com.eki.globe.KeyFigureResource.filterKeyFigures()");

		System.out.println("is precarriage " + isPreCarriage);

		System.out.println("im schedule " + includeIs);
		System.out.println("im tariff " + includeIt);
		System.out.println("inland location " + inlandLocation);
		System.out.println("inland type " + inlandGeoScopeType);
		System.out.println("country code " + countryCode);
		System.out.println("preferred port " + portLocation);
		System.out.println("inclue all pref  ports " + includeAllPrefPorts);

		System.out.println("start date " + startDate);
		System.out.println("20 " + eq20);
		System.out.println("40 " + eq40);
		System.out.println("HC " + eqHC);
		System.out.println("tp mode " + transportMode);
		if (includeAllPrefPorts) {
			portLocation=null;
		}
		String country = countryCode;
		if (country.isEmpty()) {
			country = inlandLocation.substring(0, 2);
		}
		if(transportMode.equals("ALL")) {
			transportMode = null;
		}

	List<KeyFigure> result = kfService.searchKeyFigures(inlandLocation, inlandGeoScopeType, countryCode, portLocation, transportMode, eq20, eq40);
		logger.debug("# of kfs found: {}", result.size());

		return result;
	}

}