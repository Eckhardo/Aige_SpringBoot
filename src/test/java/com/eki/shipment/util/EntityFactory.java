package com.eki.shipment.util;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.eki.shipment.model.Country;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.model.OceanRoute;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
public class EntityFactory {

    private EntityFactory() {
        throw new AssertionError();
    }

    // User

    public static Country createNewCountry() {
        return createNewCountry(randomAlphabetic(8), randomAlphabetic(2));
    }
    public static Country createNewCountry(final String name, final String code) {
        return new Country(name, code);
    }
	public static List<GeoScope> createGeoScopes() {
		List<GeoScope> geoScopes = new ArrayList<>();
		geoScopes.add(new GeoScope(randomAlphabetic(2), randomAlphabetic(5), randomAlphabetic(1), randomAlphabetic(10), true));
		geoScopes.add(new GeoScope(randomAlphabetic(2), randomAlphabetic(5), randomAlphabetic(1), randomAlphabetic(10), true));
		geoScopes.add(new GeoScope(randomAlphabetic(2), randomAlphabetic(5), randomAlphabetic(1), randomAlphabetic(10), true));
		geoScopes.add(new GeoScope(randomAlphabetic(2), randomAlphabetic(5), randomAlphabetic(1), randomAlphabetic(10), true));


		return geoScopes;
	}
	public static GeoScope createGeoScope() {
		return new GeoScope(randomAlphabetic(2), randomAlphabetic(5), randomAlphabetic(1), randomAlphabetic(10), true);
	}
	
	
	public static List<OceanRoute> createOceanRoutes() {
	 List<OceanRoute> routes= new ArrayList<>();
		
		OceanRoute simple=  new OceanRoute(randomAlphabetic(3), randomAlphabetic(3), null, Arrays.asList(randomAlphabetic(10)));
		OceanRoute withTsPort=  new OceanRoute(randomAlphabetic(3), randomAlphabetic(3), randomAlphabetic(3), Lists.newArrayList());
		OceanRoute withErrors=  new OceanRoute(randomAlphabetic(3), randomAlphabetic(3), randomAlphabetic(3), Arrays.asList(randomAlphabetic(10)));
		routes.add(simple);
		routes.add(withTsPort);
		routes.add(withErrors);
		return routes;
	}
	public final static OceanRoute createOceanRoute() {
	return    new OceanRoute(39, "BRSSZ", "565151", "HKHKG", "564077", "SGSIN", "568079", "", "", "", "", "41020SIM1763", "71010SIM1884", "", Arrays.asList("MIN_TRANSIT_RATIO"));
	 
	
	}

	public static OceanRoute createNewOceanRoute() {
		return   new OceanRoute(randomAlphabetic(3), randomAlphabetic(3), randomAlphabetic(3), Arrays.asList(randomAlphabetic(10)));
	}

	public static KeyFigure createIntermodalRoute(GeoScope from, GeoScope pod) {
		KeyFigure kf = new KeyFigure(from, null, pod, IDUtil.randomPositiveLong(), randomAlphabetic(2), true,
				randomAlphabetic(2), randomAlphabetic(2), null, randomAlphabetic(3), 1, randomAlphabetic(1), null);

		return kf;
	}
}
