package com.eki.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eki.keyfigure.KeyFigureDynamicQueryDao;
import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;

@Service
public class KeyFigureService {
	
	private static final Logger logger = LoggerFactory.getLogger(KeyFigureService.class);

	@Autowired
	private KeyFigureDynamicQueryDao keyFigureDynamicQueryDao;

	@Autowired
	private GeoScopeService geoScopeService;

	public List<KeyFigure> searchKeyFigures(String inlandLocation, String geoScopeType, String countryCode, String preferredPort, String tpMode, boolean is20, boolean is40) {
		List<String> preferredPorts = null;
		logger.warn("pref port : {}", preferredPort);
		if(! Optional.ofNullable(preferredPort).isPresent()) {
			logger.warn("Optional.ofNullable(preferredPort).isPresent() : {}", ! Optional.ofNullable(preferredPort).isPresent());
			List<GeoScope> preferredGeoScopes = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
			logger.warn("preferredGeoScopes.size : {}", preferredGeoScopes.size());

			preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);
			logger.warn("preferredPorts.size : {}", preferredPorts.size());

		}
		else {
			preferredPorts = Arrays.asList(preferredPort);
		}
		logger.warn("start kfs query");      
		List<KeyFigure> result = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType, preferredPorts, is20, is40, tpMode);
logger.warn("# kfs: {}", result.size());
		return  result;
	}
}
