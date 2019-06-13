/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.eki.model.OceanRoute;

/**
 *
 * @author ekirschning
 */
public class OceanRouteData {
    
    public static Iterable<OceanRoute> getOceanRoutes() {
         
        OceanRoute o1 = new OceanRoute(31, "BRSSZ", "115861", "HKHKG", "564077", "", "", "", "", "", "", "41020SIM1768", "", "", Collections.emptyList());
        OceanRoute o2 = new OceanRoute(32, "BRSSZ", "115861", "HKHKG", "564077", "", "", "", "", "", "", "41020SIM1763", "", "", Collections.emptyList());
        OceanRoute o3 = new OceanRoute(33, "BRSSZ", "565152 ", "HKHKG", "564077", "", "", "", "", "", "", "41060SIM1887", "", "", Collections.emptyList());
        OceanRoute o4 = new OceanRoute(48, "BRSSZ", "565151", "HKHKG", "564077", "BRSPB", "565153", "", "", "", "", "61010SIM1827", "41060SIM1886", "", Collections.emptyList());
        OceanRoute o5 = new OceanRoute(44, "BRSSZ", "1680535", "HKHKG", "564077", "ESALG", "1621517", "", "", "", "", "11050SIM0004", "71020SIM1810", "", Collections.emptyList());
        OceanRoute o6 = new OceanRoute(37, "BRSSZ", "565151", "HKHKG", "564077", "COCTG", "1528540", "COCTG", "568229", "", "", "52015SIM1670", "", "75010SIM1963", Collections.emptyList());
        
        OceanRoute o7 = new OceanRoute(47, "BRSSZ", "565151", "HKHKG", "564077", "BRRIO", "568761", "", "", "", "", "61010SIM1827", "41060SIM1886", "", Arrays.asList("MIN_TRANSIT_RATIO"));
        OceanRoute o8 = new OceanRoute(49, "BRSSZ", "565151", "HKHKG", "564077", "ARBUE", "568675", "ARBUE", "568677", "", "", "61030SIM1923", "", "41070SIM0006", Arrays.asList("NSHKF", "MIN_TRANSIT_RATIO"));
        OceanRoute o9 = new OceanRoute(86, "BRSSZ", "565151", "HKHKG", "564077", "BRPNG", "565149", "", "", "", "", "42610SIM1703", "41020SIM1768", "", Arrays.asList("REMAX", "MAX_TRANSIT_RATIO", "MIN_TRANSIT_RATIO"));
        OceanRoute o10 = new OceanRoute(39, "BRSSZ", "565151", "HKHKG", "564077", "SGSIN", "568079", "", "", "", "", "41020SIM1763", "71010SIM1884", "", Arrays.asList("MIN_TRANSIT_RATIO"));
        
        return Arrays.asList(o1, o2, o3, o4, o5, o6, o7, o8, o9, o10);
    }
}
