package com.eki.shipment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eki.shipment.dao.OceanRouteDynamicQueryDao;
import com.eki.shipment.dao.OceanRouteRepository;
import com.eki.shipment.model.OceanRoute;
import com.eki.shipment.model.RESTDateParam;

@Service
@Transactional
public class OceanRouteService extends AbstractService<OceanRoute> {

	private static final Logger logger = LoggerFactory.getLogger(OceanRouteService.class);

	@Autowired
	private OceanRouteRepository dao;
	@Autowired
	private OceanRouteDynamicQueryDao dynamicQueryDao;

	public OceanRouteService() {
		super(OceanRoute.class);
	}

	/**
	 *  Search ocean routes by search criteria.
	 *  
	 * @param includeInvalid
	 * @param includeShunting
	 * @param numberTs
	 * @param pol
	 * @param pod
	 * @param ts1
	 * @param ts2
	 * @param ts3
	 * @param startDate
	 * @param endDate
	 * @param numberOfPages
	 * @return a list of {@link OceanRoute}
	 */
	public List<OceanRoute> searchOceanRoutes(boolean includeInvalid, boolean includeShunting, String numberTs,
			String pol, String pod, String ts1, String ts2, String ts3, RESTDateParam startDate, RESTDateParam endDate,
			PageRequest numberOfPages) {
		List<OceanRoute> routes = new ArrayList<>();

		routes = dynamicQueryDao.findRoutes(pol, pod, ts1, ts2, ts3, includeShunting);
		if (!includeInvalid) {
		routes=	routes.stream().filter(or -> or.getErrors().isEmpty()).collect(Collectors.toList());
		}
		return routes;
	}

	public List<OceanRoute> findAll(Example<OceanRoute> example) {

		return dao.findAll(example);

	}

	@Override
	protected OceanRouteRepository getDao() {
		return dao;
	}

	protected OceanRouteDynamicQueryDao getDynamicQueryDao() {
		return dynamicQueryDao;
	}

}
