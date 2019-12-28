package com.eki.shipment.controller;

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

import static com.eki.common.util.QueryConstants.*;
import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.model.Country;
import com.eki.shipment.service.CountryService;

/**
 * @RestController is a convenience annotation for creating Restful controllers.
 *                 It is a specialization of @Component and is autodetected
 *                 through classpath scanning. It adds the @Controller
 *                 and @ResponseBody annotations. It converts the response to
 *                 JSON or XML. It does not work with the view technology, so
 *                 the methods cannot return ModelAndView. It is typically used
 *                 in combination with annotated handler methods based on
 *                 the @RequestMapping annotation.
 * 
 *                 The @Controller annotation is used with the view technology.
 *                 Because @PathVariable is extracting values from the URI path,
 *                 it’s not encoded. On the other hand, @RequestParam is
 *                 encoded. When to use @PathVariable?
 * 
 *                 The Most Important point to understand is when to
 *                 use @PathVariable?
 * 
 * @PathVariable is used when we have to implicate mandatory parameter and we
 *               want to pass as a URI only When we want the API to be
 *               self-explanatory for the external interface to understand it Ex
 *               – /customer/1/address = this is showing that this API will
 *               return the address of the customer having id 1
 *               <p>
 * 
 *               What is @RequestParam?
 * @RequestParam is used to handle one or more dynamic value passed by API and
 *               which need to be required by the controller method to do
 *               specific tasks.
 * 
 *               We can consider @RequestParam as a query param we passed in
 *               Servlet .
 * 
 *               Example – “/customer?customerId=1
 * 
 * @author eckha
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(value = ShipmentMappings.COUNTRY)
public class CountryController extends AbstractController<Country, Country> {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CountryService countryService;

	public CountryController() {
		super(Country.class);
	}

	/**
	 * Get the country detail based on the id passed by the client API.
	 * 
	 * @param id
	 * @return country detail
	 */
	@GetMapping(value = "{id}")
	public Country findOne(@PathVariable Long id) {
		return findOneInternal(id);
	}

	/**
	 * Get all the countries available in the underlying system
	 * 
	 * @return list of counties
	 */
	@GetMapping
	public List<Country> getCountries() {
		return countryService.findAll();
	}

	/**
	 * Create a new Country.
	 * 
	 * @return HttpRespnseHeader ( HttpStatusCode=CREATED and LOCATION uri of newly
	 *         created resource)
	 */
	@PostMapping()
	protected ResponseEntity<Object> newCountry(@RequestBody Country newCountry) {
		final Country country = createInternal(newCountry);
		// Create resource location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(country.getId())
				.toUri();
		// Send location in response
		return ResponseEntity.created(location).build();
	}

	/**
	 * Update Country.
	 * 
	 * @return HttpResponseHeader ( HttpStatusCode=OK)
	 */

	@PutMapping(value = "{id}")
	protected ResponseEntity updateCountry(@RequestBody Country country, @PathVariable Long id) {
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
	protected ResponseEntity delete(@PathVariable("id") final Long id) {
		deleteByIdInternal(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	@GetMapping(params = { SORT_BY, SORT_ORDER })
	public List<Country> findAllSorted(@RequestParam(value = SORT_BY, defaultValue = ID) final String sortBy,
			@RequestParam(value = SORT_ORDER, defaultValue = DESC) final String sortOrder) {
		 List<Country> countries=  findAllSortedInternal(sortBy, sortOrder);
		 return countries;
	}

	@Override
	@GetMapping(params = { PAGE_NO, PAGE_SIZE })
	protected List<Country> findAllPaginated(@RequestParam(value = PAGE_NO) final int pageNo,
			@RequestParam(value = PAGE_SIZE, defaultValue = "5", required=false) final int pageSize) {
		 List<Country> countries=findPaginatedInternal(pageNo, pageSize);
		 return countries;
	}

	@Override
	@GetMapping(params = { PAGE_NO, PAGE_SIZE, SORT_BY, SORT_ORDER })
	protected List<Country> findAllPaginatedAndSorted(@RequestParam(value = PAGE_NO) final int pageNo,
			@RequestParam(value = PAGE_SIZE) final int pageSize,
			@RequestParam(value = SORT_BY, defaultValue = ID) final String sortBy,
			@RequestParam(value = SORT_ORDER, defaultValue = DESC) final String sortOrder) {
		return this.findAllPaginatedAndSorted(pageNo, pageSize, sortBy, sortOrder);

	}

	@GetMapping("filter")
	public Collection<Country> searchByCode(@RequestParam(value = "country_code") String countryCode) {
		return countryService.searchCountries(countryCode);
	}

	@GetMapping("find")
	public Country findByCode(@RequestParam(value = "country_code") String countryCode) {
		return countryService.findCountry(countryCode);
	}

	@Override
	protected CountryService getService() {
		return countryService;
	}

}
