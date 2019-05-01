package com.eki.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.eki.model.OceanRoute;
import com.eki.model.RESTDateParam;
@Service
public class OceanRouteService {

	private static final Logger logger = LoggerFactory.getLogger(OceanRouteService.class);
	@Autowired
	private GeoScopeService geoScopeService;

	public List<OceanRoute> searchOceanRoutes(boolean includeInvalid, String numberTs, String pol, String pod,
			String ts1, String ts2, String ts3, String vs1, String vs2, String vs3, RESTDateParam startDate,
			RESTDateParam endDate, PageRequest of) {
		// TODO Auto-generated method stub
		return null;
	}


}
