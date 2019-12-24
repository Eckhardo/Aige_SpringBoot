package data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;

public class KeyFigureData {
	public static List<KeyFigure> getData() {

		GeoScope debrv = new GeoScope(1, "DE", "DEBRV", "L", "Bremerhaven", true);
		GeoScope deham = new GeoScope(1, "DE", "DEHAM", "L", "Hamburg", true);
		GeoScope nlrtm = new GeoScope(1, "NL", "NLRTM", "L", "Rotterdam", true);
		GeoScope beanr = new GeoScope(1, "BE", "BEANR", "L", "Antwerp", true);
		GeoScope dedus = new GeoScope(1, "DE", "DEDUS", "L", "Dusseldorf", false);
		GeoScope dusseldorf = new GeoScope(1, "DE", "DEDUS", "C", "DUSSELDORF", false);
		GeoScope dedui = new GeoScope(1, "DE", "DEDUI", "L", "Duisburg", false);

		String transportMode = "TRUCK";
		String eqGroup = "GP";
		String euro = "EUR";
		String eq20 = "20";
		String eq40 = "40";

		KeyFigure kf1 = new KeyFigure(1, dedus, null, debrv, 0, transportMode, false, eq20, "RF",
				new BigDecimal("200.00"), euro, 0, eq40, new Date());
		KeyFigure kf2 = new KeyFigure(2, dedus, null, deham, 0, transportMode, false, eq20, eqGroup,
				new BigDecimal("400.35"), euro, 0, eq40, new Date());
		KeyFigure kf3 = new KeyFigure(2, dedus, null, nlrtm, 0, transportMode, false, eq20, eqGroup,
				new BigDecimal("600.35"), euro, 0, eq40, new Date());
		KeyFigure kf4 = new KeyFigure(4, dedus, null, beanr, 0, transportMode, false, eq20, eqGroup,
				new BigDecimal("800.34"), euro, 0, eq40, new Date());
		KeyFigure kf5 = new KeyFigure(5, dedus, dedui, beanr, 0, "BARGE", true, eq20, eqGroup,
				new BigDecimal("1000.32"), euro, 0, eq40, new Date());
		KeyFigure kf6 = new KeyFigure(6, dedus, null, debrv, 0, transportMode, false, eq40, "RF",
				new BigDecimal("900.30"), euro, 0, eq40, new Date());
		KeyFigure kf7 = new KeyFigure(7, dedus, null, deham, 0, transportMode, false, eq40, eqGroup,
				new BigDecimal("1100.30"), euro, 0, eq40, new Date());
		KeyFigure kf8 = new KeyFigure(8, dedus, null, nlrtm, 0, transportMode, false, eq40, eqGroup,
				new BigDecimal("1300.35"), euro, 0, eq40, new Date());
		KeyFigure kf9 = new KeyFigure(9, dedus, null, beanr, 0, transportMode, false, eq40, eqGroup,
				new BigDecimal("1500.30"), euro, 0, eq40, new Date());
		KeyFigure kf10 = new KeyFigure(10, dedus, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1700.30"), euro, 0, eq40, new Date());
		KeyFigure kf110 = new KeyFigure(11, dedus, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1300.30"), euro, 0, eq40, new Date());
		KeyFigure kf111 = new KeyFigure(12, dedus, dedui, beanr, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1400.30"), euro, 0, eq40, new Date());
		KeyFigure kf112 = new KeyFigure(13, dedus, dedui, nlrtm, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1500.30"), euro, 0, eq40, new Date());
		KeyFigure kf113 = new KeyFigure(14, dedus, dedui, debrv, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1600.30"), euro, 0, eq40, new Date());
		KeyFigure kf114 = new KeyFigure(15, dedus, dedui, deham, 0, "RAIL", true, eq40, eqGroup,
				new BigDecimal("1000.30"), euro, 0, eq40, new Date());
		KeyFigure kf115 = new KeyFigure(16, dedus, dedui, deham, 0, "BARGE", true, eq40, eqGroup,
				new BigDecimal("700.30"), euro, 0, eq40, new Date());

		KeyFigure kf11 = new KeyFigure(17, dusseldorf, null, debrv, 0, transportMode, false, eq20, "RF",
				new BigDecimal("200.00"), euro, 0, eq40, new Date());
		KeyFigure kf12 = new KeyFigure(18, dusseldorf, null, deham, 0, transportMode, false, eq20, eqGroup,
				new BigDecimal("400.35"), euro, 0, eq40, new Date());
		KeyFigure kf13 = new KeyFigure(19, dusseldorf, null, nlrtm, 0, transportMode, false, eq20, eqGroup,
				new BigDecimal("600.35"), euro, 0, eq40, new Date());
		KeyFigure kf14 = new KeyFigure(20, dusseldorf, null, beanr, 0, transportMode, false, eq20, eqGroup,
				new BigDecimal("800.34"), euro, 0, eq40, new Date());
		KeyFigure kf15 = new KeyFigure(21, dusseldorf, dedui, beanr, 0, "BARGE", true, eq20, eqGroup,
				new BigDecimal("1000.32"), euro, 0, eq40, new Date());
		KeyFigure kf16 = new KeyFigure(22, dusseldorf, null, debrv, 0, transportMode, false, eq40, "RF",
				new BigDecimal("900.30"), euro, 0, eq40, new Date());
		KeyFigure kf17 = new KeyFigure(23, dusseldorf, null, deham, 0, transportMode, false, eq40, eqGroup,
				new BigDecimal("1100.30"), euro, 0, eq40, new Date());
		KeyFigure kf18 = new KeyFigure(24, dusseldorf, null, nlrtm, 0, transportMode, false, eq40, eqGroup,
				new BigDecimal("1300.35"), euro, 0, eq40, new Date());
		KeyFigure kf19 = new KeyFigure(25, dusseldorf, null, beanr, 0, transportMode, false, eq40, eqGroup,
				new BigDecimal("1500.30"), euro, 0, eq40, new Date());
		KeyFigure kf20 = new KeyFigure(26, dusseldorf, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1700.30"), euro, 0, eq40, new Date());
		KeyFigure kf210 = new KeyFigure(27, dusseldorf, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1300.30"), euro, 0, eq40, new Date());
		KeyFigure kf211 = new KeyFigure(28, dusseldorf, dedui, beanr, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1400.30"), euro, 0, eq40, new Date());
		KeyFigure kf212 = new KeyFigure(29, dusseldorf, dedui, nlrtm, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1500.30"), euro, 0, eq40, new Date());
		KeyFigure kf213 = new KeyFigure(30, dusseldorf, dedui, debrv, 0, "RAIL/ROAD", true, eq40, eqGroup,
				new BigDecimal("1600.30"), euro, 0, eq40, new Date());
		KeyFigure kf214 = new KeyFigure(31, dusseldorf, dedui, deham, 0, "RAIL", true, eq40, eqGroup,
				new BigDecimal("1000.30"), euro, 0, eq40, new Date());
		KeyFigure kf215 = new KeyFigure(32, dusseldorf, dedui, deham, 0, "BARGE", true, eq40, eqGroup,
				new BigDecimal("700.30"), euro, 0, eq40, new Date());

		return Arrays.asList(kf1, kf2, kf3, kf4, kf5, kf6, kf7, kf8, kf9, kf10, kf110, kf111, kf112, kf113, kf114,
				kf115, kf11, kf12, kf13, kf14, kf15, kf16, kf17, kf18, kf19, kf20, kf210, kf211, kf212, kf213, kf214,
				kf215);
	}
}
