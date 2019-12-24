package com.eki.java8;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eki.shipment.dao.AbstractRepositoryTest;
import com.eki.shipment.dao.KeyFigureDynamicQueryDao;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.service.GeoScopeService;

/**
 * Some tests for the new Java 6 {@link java.time} API including:
 * 
 * <ul>
 * <li>{@link java.time.Instant}</li>
 * <li>{@link java.time.ZoneId}</li>
 * <li>{@link java.time.ZonedDateTime}</li>
 * <li>{@link java.time.LocalDateTime}</li>
 * <li>{@link java.time.format.DateTimeFormatter}</li>
 * </ul>
 * 
 * @author ekirschning
 *
 */

public class DateTimeTest extends AbstractRepositoryTest {
	Logger logger = LoggerFactory.getLogger(DateTimeTest.class);

	@Autowired
	private KeyFigureDynamicQueryDao keyFigureDynamicQueryDao;

	@Autowired
	private GeoScopeService geoScopeService;

	List<KeyFigure> keyFigures;

	private final String inlandLocation = "DUSSELDORF";
	private String countryCode = "DE";
	private String geoScopeType = "T";

	@Before
	public void init() {
		List<GeoScope> preferredGeoScopes = geoScopeService.findPreferredGeoScopes(inlandLocation, countryCode);
		assertThat(preferredGeoScopes.size(), is(4));
		List<String> preferredPorts = geoScopeService.mapGeoScopesToPorts(preferredGeoScopes);
		assertThat(preferredPorts.size(), is(4));
		keyFigures = keyFigureDynamicQueryDao.searchKeyFigures(inlandLocation, countryCode, geoScopeType,
				preferredPorts, true, true, "TRUCK", null);

		assertThat(keyFigures.size(), is(8));

	}

	@Test
	public void convertNowUtcToDifferentTimeZones() {

		// Starting with an java.time.Instant value of now
		Instant timeStamp = Instant.now();
		logger.debug("Machine Time Now:" + timeStamp);

		// timeStamp in zone - "Europe/Paris"
		ZonedDateTime parisTZ = timeStamp.atZone(ZoneId.of("Europe/Paris"));
		logger.debug("In Paris, France, Time Zone:" + parisTZ);

		// timeStamp in zone - "Europe/Moscow"
		ZonedDateTime moscowTZ = timeStamp.atZone(ZoneId.of("Europe/Moscow"));
		logger.debug("In Moscow, Russia, Time Zone:" + moscowTZ);

	}

	@Test
	public void convertLocalDateTimeToDifferentTimeZones() {

		// Starting with an java.time.LocalDateTime value of now
		LocalDateTime localDateTime = LocalDateTime.now();
		logger.debug("LocalDateTime is:" + localDateTime);
		// timeStamp in zone - "Europe/Paris"
		ZonedDateTime parisTZ = localDateTime.atZone(ZoneId.of("Europe/Paris"));
		logger.debug("In Paris, France, Time Zone:" + parisTZ);

		// timeStamp in zone - "Europe/Moscow"
		ZonedDateTime moscowTZ = localDateTime.atZone(ZoneId.of("Europe/Moscow"));
		logger.debug("In Moscow, Russia, Time Zone:" + moscowTZ);

	}

	@Test
	public void convertJavaUtilDateToLocalDateTime() {

		Date java7Date = new java.util.Date();
		LocalDateTime localDateTime = java7Date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDate localDate = localDateTime.toLocalDate();
		logger.debug("java.util.Date value:" + java7Date);
		logger.debug("Equivalent java.time.LocalDate value:" + localDate);
		logger.debug("Equivalent java.time.LocalDateTime value:" + localDateTime);

	}

	@Test
	public void formatDateInGermanAndFrench() {

		LocalDate localDate = LocalDate.now();

		// Day of week and month in French
		String dateInFrench = localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.FRENCH));
		logger.debug("'2016-01-01' in French: " + dateInFrench);

		// Day of week and month in German
		Locale germanLocale = new Locale("de", "DE");
		String dateInGerman = localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", germanLocale));
		logger.debug("'2016-01-01' in German: " + dateInGerman);

		// German is the default locale for my system on which JVM is running
		String dateInDefault = localDate
				.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.getDefault()));
		logger.debug("'01-Jan-2016' in German(default): " + dateInDefault);

	}

	@Test
	public void convertSqlDateToLocalDateTime() {

		KeyFigure kf = keyFigures.get(0);

		// returns an instance of java.sql.Date which extends java.util.Date (does not
		// carry time information)
		java.util.Date sqlDate = new Date();
		java.util.Date utilDate = new Date(sqlDate.getTime());
		logger.debug("java.sql.Date value:" + sqlDate);
		logger.debug("java.util.Date value:" + utilDate);

		LocalDateTime localDateTime = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDate localDate = localDateTime.toLocalDate();
		logger.debug("java.util.Date value:" + utilDate);
		logger.debug("Equivalent java.time.LocalDate value:" + localDate);
		logger.debug("Equivalent java.time.LocalDateTime value:" + localDateTime);

	}

}
