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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.dto.IntermodalRouteDto;
import com.eki.shipment.dto.OceanRouteDto;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.model.RESTDateParam;
import com.eki.shipment.service.GeoScopeService;
import com.eki.shipment.service.IntermodalRouteService;
import com.google.common.collect.Lists;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(value = ShipmentMappings.INTERMODAL_ROUTE)
public class IntermodalRouteController extends AbstractController<IntermodalRouteDto, KeyFigure> {


	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IntermodalRouteService kfService;
	@Autowired
	private GeoScopeService geoScopeService;
	
	@Autowired
	private  ModelMapper modelMapper;
	
	public IntermodalRouteController() {
		super(IntermodalRouteDto.class);
	}

	/**
	 * Get the KeyFigure detail based on the id passed by the client API.
	 * 
	 * @param id
	 * @return KeyFigure detail
	 */
	@GetMapping(value = "{id}")
	public IntermodalRouteDto findOne(@PathVariable Long id) {
		return findOneInternal(id);
	}

	/**
	 * Get all the KeyFigures available in the underlying system
	 * 
	 * @return list of counties
	 */
	@GetMapping
	public List<KeyFigure> getKeyFigures() {
		return kfService.findAll();
	}

	/**
	 * Create a new KeyFigure
	 * 
	 * @return HttpStausCode=CREATED
	 */
	@PostMapping()
	protected ResponseEntity<Object> newResource(@RequestBody KeyFigure kf) {
	final IntermodalRouteDto kfNew=	 createInternal(kf);
	 //Create resource location
	URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(kfNew.getId())
            .toUri();
	 //Send location in response
    return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "{id}")
	protected ResponseEntity<Object> updateResource(@RequestBody KeyFigure kf, @PathVariable Long id) {
		updateInternal(id, kf);
		 return new ResponseEntity < >(HttpStatus.OK);
	}
	 /**
     * Deleted the KeyFigure from the system. Client will pass the ID for the KeyFigure and this 
     * end point will remove KeyFigure from the system if found.
     * @param id
     * @return
     */
	@DeleteMapping(value = "{id}")
	protected ResponseEntity<String> delete(@PathVariable("id") final Long id) {
		deleteByIdInternal(id);
		return new ResponseEntity < >(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping({ "/keyfigure/filter" })
	public List<KeyFigure> searchKeyFigures(
			@RequestParam(value = "includeImTariff", defaultValue = "false") boolean includeIt,
			@RequestParam(value = "includeImSchedule", defaultValue = "false") boolean includeIs,
			@RequestParam(value = "isPreCarriage", defaultValue = "true") boolean isPreCarriage,
			@RequestParam(value = "inlandLocation") String inlandLocation,
			@RequestParam(value = "inlandKeyFigureType") String inlandKeyFigureType,
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
			@RequestParam(value = "page", required = false, defaultValue = "0") int page) {

		logger.debug("com.eki.globe.KeyFigureResource.filterKeyFigures()");

	
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
			eqGroup = "GP";
		} else if (equipmentType.equals("REEFER")) {
			eqGroup = "RF";
		}
		List<String> preferredPorts = Lists.newArrayList();
		if (includeAllPrefPorts) {
			List<GeoScope> preferredGeoScope = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
			preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScope);
		}
		List<KeyFigure> kfs = kfService.searchKeyFigures(inlandLocation, inlandKeyFigureType, countryCode, preferredPorts,
				transportMode, eq20, eq40, eqGroup, PageRequest.of(page, 5));
		if (kfs.isEmpty())
			throw new IntermodalRouteNotFoundException();
		return kfs;
	}

	@GetMapping({ "/keyfigure/find" })
	public List<KeyFigure> searchSimple(@RequestParam(value = "inlandLocation") String inlandLocation,
			@RequestParam(value = "inlandKeyFigureType") String inlandKeyFigureType,
			@RequestParam(value = "countryCode") String countryCode,
			@RequestParam(value = "portLocation") String portLocation,
			@RequestParam(value = "includeAllPrefPorts", defaultValue = "true") boolean includeAllPrefPorts) {

		logger.debug("com.eki.globe.KeyFigureResource.filterKeyFigures()");

		String country = countryCode;
		if (country.isEmpty()) {
			country = inlandLocation.substring(0, 2);
		}
		List<String> preferredPorts = Lists.newArrayList();
		if (includeAllPrefPorts) {
			List<GeoScope> preferredGeoScope = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
			preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScope);
	}

		List<KeyFigure> kfs = kfService.searchKeyFiguresSimple(inlandLocation, inlandKeyFigureType, countryCode,
				preferredPorts);
		if (kfs.isEmpty())
			throw new IntermodalRouteNotFoundException();
		return kfs;
	}

	@Override
	protected  IntermodalRouteService getService() {
		return kfService;
	}


	@GetMapping(params = { SORT_BY, SORT_ORDER })
	public List<IntermodalRouteDto> findAllSorted(@RequestParam(value = SORT_BY, defaultValue = ID) final String sortBy,
			@RequestParam(value = SORT_ORDER, defaultValue = DESC) final String sortOrder) {
		return findAllSortedInternal(sortBy, sortOrder);
		
	}

	@GetMapping(params = { PAGE_NO, PAGE_SIZE })
	protected List<IntermodalRouteDto> findAllPaginated(@RequestParam(value = PAGE_NO) final int pageNo,
			@RequestParam(value = PAGE_SIZE) final int pageSize) {
		return findPaginatedInternal(pageNo, pageSize);
	}

	@GetMapping(params = { PAGE_NO, PAGE_SIZE, SORT_BY, SORT_ORDER })
	protected List<IntermodalRouteDto> findAllPaginatedAndSorted(@RequestParam(value = PAGE_NO) final int pageNo,
			@RequestParam(value = PAGE_SIZE) final int pageSize,
			@RequestParam(value = SORT_BY, defaultValue = ID) final String sortBy,
			@RequestParam(value = SORT_ORDER, defaultValue = DESC) final String sortOrder) {
		return this.findAllPaginatedAndSorted(pageNo, pageSize, sortBy, sortOrder);

	}

	@Override
	protected IntermodalRouteDto convertToDto(KeyFigure entity) {
		return modelMapper.map(entity, IntermodalRouteDto.class);
	}

	@Override
	protected KeyFigure convertToEntity(IntermodalRouteDto dto) {
		
		return modelMapper.map(dto, KeyFigure.class);
	}

}
