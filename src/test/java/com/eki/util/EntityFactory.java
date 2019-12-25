package com.eki.util;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eki.shipment.model.Country;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.OceanRoute;
import com.google.common.collect.Sets;

public class EntityFactory {

    private EntityFactory() {
        throw new AssertionError();
    }

    // User

    public static Country createNewCountry() {
        return createNewCountry("Germany", "DE");
    }
    public static Country createNewCountry(final String name, final String code) {
        return new Country(name, code);
    }
	public static List<GeoScope> createGeoScopes() {
		List<GeoScope> geoScopes = new ArrayList<>();
		GeoScope deham = new GeoScope();
		deham.setCountryCode("DE");
		deham.setLocationCode("DEHAM");
		deham.setGeoScopeType("L");
		geoScopes.add(deham);

		GeoScope dedus = new GeoScope();
		dedus.setCountryCode("DE");
		dedus.setLocationCode("DEDUS");
		dedus.setGeoScopeType("L");
		geoScopes.add(dedus);

		GeoScope dedui = new GeoScope();
		dedui.setCountryCode("DE");
		dedui.setLocationCode("DEDUI");
		dedui.setGeoScopeType("L");
		geoScopes.add(dedui);

		GeoScope brssz = new GeoScope();
		brssz.setCountryCode("BR");
		brssz.setLocationCode("BRSSZ");
		brssz.setGeoScopeType("L");
		geoScopes.add(brssz);
		return geoScopes;
	}
	public static GeoScope createGeoScope() {
		return createGeoScopes().get(0);
	}
	
	
	public static List<OceanRoute> createOceanRoutes() {
	 List<OceanRoute> routes= new ArrayList<>();
		
		OceanRoute simple= new OceanRoute("BRSSZ", "HKHKG", null, new ArrayList<>());
		OceanRoute withTsPort= new OceanRoute("BRSSZ", "HKHKG", "ARBUE", new ArrayList<>());
		OceanRoute withErrors= new OceanRoute("BRSSZ", "HKHKG", "ARBUE", Arrays.asList("NO_SHUNTING"));
		routes.add(simple);
		routes.add(withTsPort);
		routes.add(withErrors);
		return routes;
	}
	public static OceanRoute createOceanRoute() {
		return createOceanRoutes().get(0);
	}
}
