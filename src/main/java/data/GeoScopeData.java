package data;

import java.util.Arrays;
import java.util.List;

import com.eki.shipment.model.GeoScope;

public class GeoScopeData {

	
	public static List<GeoScope> getData() {

		GeoScope debrv = new GeoScope("DE", "DEBRV", "L", "Bremerhaven", true);
		GeoScope deham = new GeoScope("DE", "DEHAM", "L", "Hamburg", true);
		GeoScope nlrtm = new GeoScope("NL", "NLRTM", "L", "Rotterdam", true);
		GeoScope beanr = new GeoScope("BE", "BEANR", "L", "Antwerp", true);
		GeoScope dedus = new GeoScope("DE", "DEDUS", "L", "Dusseldorf", false);
		GeoScope dusseldorf = new GeoScope("DE", "DEDUS", "C", "DUSSELDORF", false);
		GeoScope dedui = new GeoScope("DE", "DEDUI", "L", "Duisburg", false);

		return Arrays.asList(debrv,deham,nlrtm,beanr,dedus,dedui,dusseldorf);
	}
}
