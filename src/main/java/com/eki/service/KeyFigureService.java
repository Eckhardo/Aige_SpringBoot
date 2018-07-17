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
		if(! Optional.ofNullable(preferredPort).isPresent()) {
			List<GeoScope> preferredGeoScopes = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
	
			preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);

		}
		else {
			preferredPorts = Arrays.asList(preferredPort);
		}
		List<KeyFigure> result = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType, preferredPorts, is20, is40, tpMode);
logger.debug("# kfs: {}", result.size());
		return  result;
	}
}
