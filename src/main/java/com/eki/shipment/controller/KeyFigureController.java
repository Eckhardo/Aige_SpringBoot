package com.eki.shipment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.model.RESTDateParam;
import com.eki.shipment.service.KeyFigureService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders="*")
@RestController
public class KeyFigureController {

	private static final Logger logger = LoggerFactory.getLogger(KeyFigureController.class);

	@Autowired
	private KeyFigureService kfService;

	@GetMapping({ "/keyfigure/filter" })
	public List<KeyFigure> searchKeyFigures(
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
			@RequestParam(value = "endDate") RESTDateParam endDate,
			@RequestParam(value = "page", required=false, defaultValue="0") int page) {
            
		logger.debug("com.eki.globe.KeyFigureResource.filterKeyFigures()");

		if (includeAllPrefPorts) {
			portLocation = null;
		}
		String country = countryCode;
		if (country.isEmpty()) {
			country = inlandLocation.substring(0, 2);
		}
		if (transportMode.equals("ALL")) {
			transportMode = null;
		}
		logger.debug("eq:" + equipmentType);


		String eqGroup = null;
		if (equipmentType.equals("GENERAL")) {
			eqGroup ="GP";
		}
		else if(equipmentType.equals("REEFER")) {
			eqGroup ="RF";
		}

		List<KeyFigure> kfs=  kfService.searchKeyFigures(inlandLocation, inlandGeoScopeType, countryCode,
				portLocation, transportMode, eq20, eq40, eqGroup ,PageRequest.of(page, 5));
	     if(kfs.isEmpty())throw new KeyFigureNotFoundException();
	     return kfs;
	}
	@GetMapping({ "/keyfigure/find" })
	public List<KeyFigure> searchSimple(
				@RequestParam(value = "inlandLocation") String inlandLocation,
			@RequestParam(value = "inlandGeoScopeType") String inlandGeoScopeType,
			@RequestParam(value = "countryCode") String countryCode,
			@RequestParam(value = "portLocation") String portLocation,
			@RequestParam(value = "includeAllPrefPorts", defaultValue = "true") boolean includeAllPrefPorts) {
            
		logger.debug("com.eki.globe.KeyFigureResource.filterKeyFigures()");

		if (includeAllPrefPorts) {
			portLocation = null;
		}
		String country = countryCode;
		if (country.isEmpty()) {
			country = inlandLocation.substring(0, 2);
		}
		List<KeyFigure> kfs=  kfService.searchKeyFiguresSimple(inlandLocation, inlandGeoScopeType, countryCode,
				portLocation);
	     if(kfs.isEmpty())throw new KeyFigureNotFoundException();
	     return kfs;
	}
}
