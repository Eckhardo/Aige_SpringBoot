package com.eki.shipment.controller;

import static com.eki.common.util.QueryConstants.DESC;
import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.PAGE_NO;
import static com.eki.common.util.QueryConstants.PAGE_SIZE;
import static com.eki.common.util.QueryConstants.SORT_BY;
import static com.eki.common.util.QueryConstants.SORT_ORDER;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.model.Country;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.service.GeoScopeService;
import com.eki.shipment.service.IServiceOperations;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders="*")
@RestController
@RequestMapping(value = ShipmentMappings.GEOSCOPE)
public class GeoScopeController extends AbstractController<GeoScope, GeoScope> {

	Logger logger = LoggerFactory.getLogger(getClass());
	 

	@Autowired
	private GeoScopeService geoScopeService;
	
	public GeoScopeController() {
		super(GeoScope.class);
	}

	/**
	 * Get the GeoScope detail based on the id passed by the client API.
	 * 
	 * @param id
	 * @return GeoScope detail
	 */
	@GetMapping(value = "{id}")
	public GeoScope findOne(@PathVariable Long id) {
		return findOneInternal(id);
	}

	/**
	 * Get all the GeoScopes available in the underlying system
	 * 
	 * @return list of counties
	 */
	@GetMapping
	public List<GeoScope> getGeoScopes() {
		return geoScopeService.findAll();
	}

	/**
	 * Create a new GeoScope
	 * 
	 * @return HttpStausCode=CREATED
	 */
	@PostMapping()
	protected ResponseEntity<Object> newGeoScope(@RequestBody GeoScope newGeoScope) {
	final GeoScope GeoScope=	 createInternal(newGeoScope);
	 //Create resource location
	URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(GeoScope.getId())
            .toUri();
	 //Send location in response
    return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "{id}")
	protected ResponseEntity update(@RequestBody GeoScope GeoScope, @PathVariable Long id) {
		updateInternal(id, GeoScope);
		 return new ResponseEntity < >(HttpStatus.OK);
	}
	 /**
     * Deleted the GeoScope from the system. Client will pass the ID for the GeoScope and this 
     * end point will remove GeoScope from the system if found.
     * @param id
     * @return
     */
	@DeleteMapping(value = "{id}")
	protected ResponseEntity delete(@PathVariable("id") final Long id) {
		deleteByIdInternal(id);
		return new ResponseEntity < >(HttpStatus.NO_CONTENT);
	}
	

	@GetMapping({ "/filter" })
	public List<GeoScope> searchGeoScope( @RequestParam(value = "location_code", required = true) String locationCode,
			@RequestParam(value = "geo_scope_type", required = true) String geoScopeType,
			@RequestParam(value = "geo_scope_code", required = false) String GeoScopeCode) {
 		return geoScopeService.searchGeoScopes(locationCode, geoScopeType, GeoScopeCode);

	}

	
	@GetMapping({ "/preferredPort" })
	public List<GeoScope> searchPreferredPort(@RequestParam(value = "location_code", required = true) String locationCode,
			@RequestParam(value = "geo_scope_type", required = true) String geoScopeType,
			@RequestParam(value = "country_code", required = false) String GeoScopeCode) {


		return geoScopeService.findPreferredGeoScopes(locationCode,  GeoScopeCode);
	}
	
	
	@GetMapping({ "/ports" })
	public List<GeoScope> searchPorts(@RequestParam(value = "location_code", required = true) String locationCode) {
		return geoScopeService.findPorts(locationCode);
	
	}

	@Override
	protected GeoScopeService getService() {
	return geoScopeService;
	}

	@Override
	@GetMapping(params = { SORT_BY, SORT_ORDER })
	public List<GeoScope> findAllSorted(@RequestParam(value = SORT_BY, defaultValue = ID) final String sortBy,
			@RequestParam(value = SORT_ORDER, defaultValue = DESC) final String sortOrder) {
		 List<GeoScope> countries=  findAllSortedInternal(sortBy, sortOrder);
		 return countries;
	}

	@Override
	@GetMapping(params = { PAGE_NO, PAGE_SIZE })
	protected List<GeoScope> findAllPaginated(@RequestParam(value = PAGE_NO) final int pageNo,
			@RequestParam(value = PAGE_SIZE) final int pageSize) {
		return findPaginatedInternal(pageNo, pageSize);
	}

	@Override
	@GetMapping(params = { PAGE_NO, PAGE_SIZE, SORT_BY, SORT_ORDER })
	protected List<GeoScope> findAllPaginatedAndSorted(@RequestParam(value = PAGE_NO) final int pageNo,
			@RequestParam(value = PAGE_SIZE) final int pageSize,
			@RequestParam(value = SORT_BY, defaultValue = ID) final String sortBy,
			@RequestParam(value = SORT_ORDER, defaultValue = DESC) final String sortOrder) {
		return this.findAllPaginatedAndSorted(pageNo, pageSize, sortBy, sortOrder);

	}


}
