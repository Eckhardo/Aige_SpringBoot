package com.eki.shipment.controller;
import static com.eki.common.util.QueryConstants.DESC;
import static com.eki.common.util.QueryConstants.ID;
import static com.eki.common.util.QueryConstants.PAGE_NO;
import static com.eki.common.util.QueryConstants.PAGE_SIZE;
import static com.eki.common.util.QueryConstants.SORT_BY;
import static com.eki.common.util.QueryConstants.SORT_ORDER;


import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import static com.eki.common.util.ShipmentMappings.OCEAN_ROUTE;
import com.eki.shipment.dto.OceanRouteDto;
import com.eki.shipment.model.OceanRoute;
import com.eki.shipment.model.RESTDateParam;
import com.eki.shipment.service.OceanRouteService;



@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(value =OCEAN_ROUTE)
public class OceanRouteController  extends AbstractController<OceanRouteDto, OceanRoute> {



	@Autowired
	private OceanRouteService oceanRouteService;
	
	@Autowired
	ModelMapper modelMapper;

	public OceanRouteController() {
		super(OceanRouteDto.class);
	}

	/**
	 * Get the country detail based on the id passed by the client API.
	 * 
	 * @param id
	 * @return country detail
	 */
	@GetMapping(value = "{id}")
	public OceanRouteDto findOne(@PathVariable Long id) {
		return findOneInternal(id);
	}

	/**
	 * Get all the countries available in the underlying system
	 * 
	 * @return list of counties
	 */
	@GetMapping
	public List<OceanRouteDto> findAll() {
		return findAllInternal();
	}

	/**
	 * Create a new OceanRoute.
	 * 
	 * @return HttpRespnseHeader ( HttpStatusCode=CREATED and LOCATION uri of newly
	 *         created resource)
	 */
	@PostMapping()
	protected ResponseEntity<Object> createResource(@RequestBody OceanRoute newOceanRoute) {
		final OceanRouteDto country = createInternal(newOceanRoute);
		// Create resource location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(country.getId())
				.toUri();
		// Send location in response
		return ResponseEntity.created(location).build();
	}

	/**
	 * Update OceanRoute.
	 * 
	 * @return HttpResponseHeader ( HttpStatusCode=OK)
	 */

	@PutMapping(value = "{id}")
	protected ResponseEntity<String> updateResource(@RequestBody OceanRoute country, @PathVariable Long id) {
		updateInternal(id, country);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Deleted the country from the system. Client will pass the ID for the country
	 * and this end point will remove country from the system if found.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "{id}")
	protected ResponseEntity<String> deleteResource(@PathVariable("id") final Long id) {
		deleteByIdInternal(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


	@GetMapping(params = { SORT_BY, SORT_ORDER })
	public List<OceanRouteDto> findAllSorted(@RequestParam(value = SORT_BY, defaultValue = ID) final String sortBy,
			@RequestParam(value = SORT_ORDER, defaultValue = DESC) final String sortOrder) {
	return   findAllSortedInternal(sortBy, sortOrder);
		
	}


	@GetMapping(params = { PAGE_NO, PAGE_SIZE })
	protected List<OceanRouteDto> findAllPaginated(@RequestParam(value = PAGE_NO) final int pageNo,
			@RequestParam(value = PAGE_SIZE) final int pageSize) {
		return findPaginatedInternal(pageNo, pageSize);
	
	}


	@GetMapping(params = { PAGE_NO, PAGE_SIZE, SORT_BY, SORT_ORDER })
	protected List<OceanRouteDto> findAllPaginatedAndSorted(@RequestParam(value = PAGE_NO) final int pageNo,
			@RequestParam(value = PAGE_SIZE) final int pageSize,
			@RequestParam(value = SORT_BY, defaultValue = ID) final String sortBy,
			@RequestParam(value = SORT_ORDER, defaultValue = DESC) final String sortOrder) {
		return this.findAllPaginatedAndSortedInternal(pageNo, pageSize, sortBy, sortOrder);

	}





	@GetMapping({ "/filter" })
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
		return getService().searchOceanRoutes(includeInvalid, includeShunting, numberTs, pol, pod, ts1, ts2, ts3,
				startDate, endDate, PageRequest.of(page, 5));

	}
	// Single item

	@Override
	protected OceanRouteService getService() {
		return oceanRouteService;
	}

	@Override
	protected OceanRouteDto convertToDto(OceanRoute entity) {
	
	OceanRouteDto dto= modelMapper.map(entity, OceanRouteDto.class);
	return dto;
	}

	@Override
	protected OceanRoute convertToEntity(OceanRouteDto dto) {
	
	OceanRoute entity= modelMapper.map(dto, OceanRoute.class);
	return entity;
	}
}
