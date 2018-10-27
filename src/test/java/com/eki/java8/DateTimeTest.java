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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.keyfigure.KeyFigureDynamicQueryDao;
import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;
import com.eki.service.GeoScopeService;

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
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations="classpath:application-test.properties")
public class DateTimeTest {

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
		System.out.println("Machine Time Now:" + timeStamp);

		// timeStamp in zone - "Europe/Paris"
		ZonedDateTime parisTZ = timeStamp.atZone(ZoneId.of("Europe/Paris"));
		System.out.println("In Paris, France, Time Zone:" + parisTZ);

		// timeStamp in zone - "Europe/Moscow"
		ZonedDateTime moscowTZ = timeStamp.atZone(ZoneId.of("Europe/Moscow"));
		System.out.println("In Moscow, Russia, Time Zone:" + moscowTZ);

	}

	@Test
	public void convertLocalDateTimeToDifferentTimeZones() {

		// Starting with an java.time.LocalDateTime value of now
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("LocalDateTime is:" + localDateTime);
		// timeStamp in zone - "Europe/Paris"
		ZonedDateTime parisTZ = localDateTime.atZone(ZoneId.of("Europe/Paris"));
		System.out.println("In Paris, France, Time Zone:" + parisTZ);

		// timeStamp in zone - "Europe/Moscow"
		ZonedDateTime moscowTZ = localDateTime.atZone(ZoneId.of("Europe/Moscow"));
		System.out.println("In Moscow, Russia, Time Zone:" + moscowTZ);

	}

	@Test
	public void convertJavaUtilDateToLocalDateTime() {

		Date java7Date = new java.util.Date();
		LocalDateTime localDateTime = java7Date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDate localDate = localDateTime.toLocalDate();
		System.out.println("java.util.Date value:" + java7Date);
		System.out.println("Equivalent java.time.LocalDate value:" + localDate);
		System.out.println("Equivalent java.time.LocalDateTime value:" + localDateTime);

	}

	@Test
	public void formatDateInGermanAndFrench() {

		LocalDate localDate = LocalDate.now();

		// Day of week and month in French
		String dateInFrench = localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.FRENCH));
		System.out.println("'2016-01-01' in French: " + dateInFrench);

		// Day of week and month in German
		Locale germanLocale = new Locale("de", "DE");
		String dateInGerman = localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", germanLocale));
		System.out.println("'2016-01-01' in German: " + dateInGerman);

		// German is the default locale for my system on which JVM is running
		String dateInDefault = localDate
				.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.getDefault()));
		System.out.println("'01-Jan-2016' in German(default): " + dateInDefault);

	}

	@Test
	public void convertSqlDateToLocalDateTime() {

		KeyFigure kf = keyFigures.get(0);

		// returns an instance of java.sql.Date which extends java.util.Date (does not
		// carry time information)
		java.util.Date sqlDate = kf.getStartDate();
		java.util.Date utilDate = new Date(sqlDate.getTime());
		System.out.println("java.sql.Date value:" + sqlDate);
		System.out.println("java.util.Date value:" + utilDate);

		LocalDateTime localDateTime = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDate localDate = localDateTime.toLocalDate();
		System.out.println("java.util.Date value:" + utilDate);
		System.out.println("Equivalent java.time.LocalDate value:" + localDate);
		System.out.println("Equivalent java.time.LocalDateTime value:" + localDateTime);

	}

}
