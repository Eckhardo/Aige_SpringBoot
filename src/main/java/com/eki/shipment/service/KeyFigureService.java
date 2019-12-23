package com.eki.shipment.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.eki.shipment.dao.KeyFigureDynamicQueryDao;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;

@Service
public class KeyFigureService {

	private static final Logger logger = LoggerFactory.getLogger(KeyFigureService.class);

	@Autowired
	private KeyFigureDynamicQueryDao keyFigureDynamicQueryDao;

	@Autowired
	private GeoScopeService geoScopeService;

	public List<KeyFigure> searchKeyFigures(String inlandLocation, String geoScopeType, String countryCode,
			String preferredPort, String tpMode, boolean is20, boolean is40, String eqGroup, PageRequest pageRequest) {
		List<String> preferredPorts = null;
		if (!Optional.ofNullable(preferredPort).isPresent()) {
			List<GeoScope> preferredGeoScopes = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
			logger.debug("# preferredGeoScopes: {}", preferredGeoScopes.size());
			preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);

		} else {
			preferredPorts = Arrays.asList(preferredPort);
		}
		
		List<KeyFigure> result = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType,
				preferredPorts, is20, is40, tpMode,eqGroup);
		logger.debug("# kf: {}", result.size());

		
		return result;
	}

	public List<KeyFigure> searchKeyFiguresSimple(String inlandLocation, String inlandGeoScopeType, String countryCode,
			String portLocation) {
			List<GeoScope> preferredGeoScopes = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
			logger.debug("# preferredGeoScopes: {}", preferredGeoScopes.size());
			List<String> 	preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);

		
		
		List<KeyFigure> result = keyFigureDynamicQueryDao.searchKeyFiguresSimple(inlandLocation, countryCode, inlandGeoScopeType,
				preferredPorts);
		logger.debug("# kf: {}", result.size());

		
		return result;	}
}
