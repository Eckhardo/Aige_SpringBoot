package com.eki.shipment.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eki.shipment.dao.OceanRouteRepository;
import com.eki.shipment.model.OceanRoute;
import com.eki.shipment.model.RESTDateParam;
import com.eki.shipment.service.OceanRouteService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
public class OceanRouteController {

	private static final Logger logger = LoggerFactory.getLogger(OceanRouteController.class);

	@Autowired
	private OceanRouteService oceanRouteService;
	@Autowired
	private OceanRouteRepository oceanRouteDao;

	@GetMapping({ "/oceanroute/filter" })
	public List<OceanRoute> search(
			@RequestParam(value = "includeInvalid", defaultValue = "false") boolean includeInvalid,
			@RequestParam(value = "includeShunting", defaultValue = "false") boolean includeShunting,
			@RequestParam(value = "numberTs", defaultValue = "1") String numberTs,
			@RequestParam(value = "pol") String pol, @RequestParam(value = "pod") String pod,
			@RequestParam(value = "ts1", required = false) String ts1,
			@RequestParam(value = "ts2", required = false) String ts2,
			@RequestParam(value = "ts3", required = false) String ts3,
			@RequestParam(value = "startDate", required = false) RESTDateParam startDate,
			@RequestParam(value = "endDate", required = false) RESTDateParam endDate,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
		return oceanRouteService.searchOceanRoutes(includeInvalid, includeShunting, numberTs, pol, pod, ts1, ts2, ts3,
				startDate, endDate, PageRequest.of(page, 5));

	}
	// Single item

	@GetMapping("/oceanroute/{id}")
	OceanRoute one(@PathVariable Long id) {

		return oceanRouteDao.findById(id).get();
	}

	@PostMapping("/oceanroute")
	OceanRoute newRoute(@RequestBody OceanRoute newRoute) {
		return oceanRouteDao.save(newRoute);
	}

	@PutMapping("/oceanroute/{id}")
	OceanRoute replaceRoute(@RequestBody OceanRoute newOceanRoute, @PathVariable Long id) {

		Optional<OceanRoute> route = oceanRouteDao.findById(id);
		if (route.isPresent()) {
			route.get().setPol(newOceanRoute.getPol());
			route.get().setPod(newOceanRoute.getPod());
			route.get().setTs1(newOceanRoute.getTs1());
			return oceanRouteDao.save(route.get());
		} else {
			return oceanRouteDao.save(newOceanRoute);
		}

	}

	@DeleteMapping("/oceanroute/{id}")
	void deleteRoute(@PathVariable Long id) {
		oceanRouteDao.deleteById(id);
	}
}
