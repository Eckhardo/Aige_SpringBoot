package data;

import java.util.Arrays;
import java.util.List;

import com.eki.shipment.model.GeoScope;

public class GeoScopeData {

	
	public static List<GeoScope> getData() {

		GeoScope debrv = new GeoScope(1, "DE", "DEBRV", "L", "Bremerhaven", true);
		GeoScope deham = new GeoScope(1, "DE", "DEHAM", "L", "Hamburg", true);
		GeoScope nlrtm = new GeoScope(1, "NL", "NLRTM", "L", "Rotterdam", true);
		GeoScope beanr = new GeoScope(1, "BE", "BEANR", "L", "Antwerp", true);
		GeoScope dedus = new GeoScope(1, "DE", "DEDUS", "L", "Dusseldorf", false);
		GeoScope dusseldorf = new GeoScope(1, "DE", "DEDUS", "C", "DUSSELDORF", false);
		GeoScope dedui = new GeoScope(1, "DE", "DEDUI", "L", "Duisburg", false);

		return Arrays.asList(debrv,deham,nlrtm,beanr,dedus,dedui,dusseldorf);
	}
}
