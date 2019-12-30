package com.eki.shipment.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.eki.shipment.dao.IntermodalRouteDynamicQueryDao;
import com.eki.shipment.dao.IntermodalRouteRepository;
import com.eki.shipment.model.KeyFigure;

@Service
public class IntermodalRouteService extends AbstractService<KeyFigure>{
	
	@Autowired
	private IntermodalRouteRepository dao;

	@Autowired
	private IntermodalRouteDynamicQueryDao dynamicQueryDao;

	public IntermodalRouteService() {
		super(KeyFigure.class);
	}

	private static final Logger logger = LoggerFactory.getLogger(IntermodalRouteService.class);


	public List<KeyFigure> searchKeyFigures(String inlandLocation, String geoScopeType, String countryCode,
			List<String> preferredPorts, String tpMode, boolean is20, boolean is40, String eqGroup,
			PageRequest pageRequest) {
		return dynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType, preferredPorts,
				is20, is40, tpMode, eqGroup);

	}

	public List<KeyFigure> searchKeyFiguresSimple(String inlandLocation, String inlandGeoScopeType, String countryCode,
			List<String> preferredPorts) {

		return dynamicQueryDao.searchKeyFiguresSimple(inlandLocation, countryCode, inlandGeoScopeType,
				preferredPorts);
	}

	@Override
	protected IntermodalRouteRepository getDao() {
		return dao;
	}
	
	protected IntermodalRouteDynamicQueryDao getDynamicQueryDao() {
		return dynamicQueryDao;
	}
}
