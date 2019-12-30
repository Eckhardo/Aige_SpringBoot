package data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;

public class IntermodalRouteFigureData {

	static String transportMode = "TRUCK";
	static String eqGroup = "GP";
	static String euro = "EUR";
	static String eq20 = "20";
	static String eq40 = "40";

	public static KeyFigure getSingle() {
		GeoScope deham = new GeoScope("DE", "DEHAM", "L", "Hamburg", true);
		GeoScope beanr = new GeoScope("BE", "BEANR", "L", "Antwerp", true);
		KeyFigure kf1 = new KeyFigure(deham, null, beanr, 0, transportMode, false, eq20, "RF", new BigDecimal("200.00"),
				euro, 0, eq40, new Date());
		return kf1;
	}

	public static List<KeyFigure> getData() {

		GeoScope debrv = new GeoScope("DE", "DEBRV", "L", "Bremerhaven", true);
		GeoScope deham = new GeoScope("DE", "DEHAM", "L", "Hamburg", true);
		GeoScope nlrtm = new GeoScope("NL", "NLRTM", "L", "Rotterdam", true);
		GeoScope beanr = new GeoScope("BE", "BEANR", "L", "Antwerp", true);
		GeoScope dedus = new GeoScope("DE", "DEDUS", "L", "Dusseldorf", false);
		GeoScope dusseldorf = new GeoScope("DE", "DEDUS", "C", "DUSSELDORF", false);
		GeoScope dedui = new GeoScope("DE", "DEDUI", "L", "Duisburg", false);

		KeyFigure kf1 = new KeyFigure(dedus, null, debrv, 0, transportMode, false, eq20, "RF", new BigDecimal("200.00"),
				euro, 0, eq40, new Date());
		KeyFigure kf2 = new KeyFigure(dedus, null, deham, 0, transportMode, false, eq20, eqGroup, new BigDecimal("400.35"),
				euro, 0, eq40, new Date());
		KeyFigure kf3 = new KeyFigure(dedus, null, nlrtm, 0, transportMode, false, eq20, eqGroup, new BigDecimal("600.35"),
				euro, 0, eq40, new Date());
		KeyFigure kf4 = new KeyFigure(dedus, null, beanr, 0, transportMode, false, eq20, eqGroup, new BigDecimal("800.34"),
				euro, 0, eq40, new Date());
		KeyFigure kf5 = new KeyFigure(dedus, dedui, beanr, 0, "BARGE", true, eq20, eqGroup, new BigDecimal("1000.32"),
				euro, 0, eq40, new Date());
		KeyFigure kf6 = new KeyFigure(dedus, null, debrv, 0, transportMode, false, eq40, "RF", new BigDecimal("900.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf7 = new KeyFigure(dedus, null, deham, 0, transportMode, false, eq40, eqGroup, new BigDecimal("1100.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf8 = new KeyFigure(dedus, null, nlrtm, 0, transportMode, false, eq40, eqGroup, new BigDecimal("1300.35"),
				euro, 0, eq40, new Date());
		KeyFigure kf9 = new KeyFigure(dedus, null, beanr, 0, transportMode, false, eq40, eqGroup, new BigDecimal("1500.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf10 = new KeyFigure(dedus, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1700.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf110 = new KeyFigure(dedus, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1300.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf111 = new KeyFigure(dedus, dedui, beanr, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1400.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf112 = new KeyFigure(dedus, dedui, nlrtm, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1500.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf113 = new KeyFigure(dedus, dedui, debrv, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1600.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf114 = new KeyFigure(dedus, dedui, deham, 0, "RAIL", true, eq40, eqGroup, new BigDecimal("1000.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf115 = new KeyFigure(dedus, dedui, deham, 0, "BARGE", true, eq40, eqGroup, new BigDecimal("700.30"),
				euro, 0, eq40, new Date());

		KeyFigure kf11 = new KeyFigure(dusseldorf, null, debrv, 0, transportMode, false, eq20, "RF", new BigDecimal("200.00"),
				euro, 0, eq40, new Date());
		KeyFigure kf12 = new KeyFigure(dusseldorf, null, deham, 0, transportMode, false, eq20, eqGroup, new BigDecimal("400.35"),
				euro, 0, eq40, new Date());
		KeyFigure kf13 = new KeyFigure(dusseldorf, null, nlrtm, 0, transportMode, false, eq20, eqGroup, new BigDecimal("600.35"),
				euro, 0, eq40, new Date());
		KeyFigure kf14 = new KeyFigure(dusseldorf, null, beanr, 0, transportMode, false, eq20, eqGroup, new BigDecimal("800.34"),
				euro, 0, eq40, new Date());
		KeyFigure kf15 = new KeyFigure(dusseldorf, dedui, beanr, 0, "BARGE", true, eq20, eqGroup, new BigDecimal("1000.32"),
				euro, 0, eq40, new Date());
		KeyFigure kf16 = new KeyFigure(dusseldorf, null, debrv, 0, transportMode, false, eq40, "RF", new BigDecimal("900.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf17 = new KeyFigure(dusseldorf, null, deham, 0, transportMode, false, eq40, eqGroup, new BigDecimal("1100.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf18 = new KeyFigure(dusseldorf, null, nlrtm, 0, transportMode, false, eq40, eqGroup, new BigDecimal("1300.35"),
				euro, 0, eq40, new Date());
		KeyFigure kf19 = new KeyFigure(dusseldorf, null, beanr, 0, transportMode, false, eq40, eqGroup, new BigDecimal("1500.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf20 = new KeyFigure(dusseldorf, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1700.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf210 = new KeyFigure(dusseldorf, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1300.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf211 = new KeyFigure(dusseldorf, dedui, beanr, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1400.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf212 = new KeyFigure(dusseldorf, dedui, nlrtm, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1500.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf213 = new KeyFigure(dusseldorf, dedui, debrv, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1600.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf214 = new KeyFigure(dusseldorf, dedui, deham, 0, "RAIL", true, eq40, eqGroup, new BigDecimal("1000.30"),
				euro, 0, eq40, new Date());
		KeyFigure kf215 = new KeyFigure(dusseldorf, dedui, deham, 0, "BARGE", true, eq40, eqGroup, new BigDecimal("700.30"),
				euro, 0, eq40, new Date());

		return Arrays.asList(kf1, kf2, kf3, kf4, kf5, kf6, kf7, kf8, kf9, kf10, kf110, kf111, kf112, kf113, kf114,
				kf115, kf11, kf12, kf13, kf14, kf15, kf16, kf17, kf18, kf19, kf20, kf210, kf211, kf212, kf213, kf214,
				kf215);
	}
	
	
}
