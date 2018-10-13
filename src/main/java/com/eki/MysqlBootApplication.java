package com.eki;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.eki.geoscope.GeoScopeRepository;
import com.eki.keyfigure.KeyFigureRepository;
import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;
import com.eki.service.GeoScopeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan("com.eki")
//Spring Boot will automatically perform a component scan in the package of the Spring Boot main class and below.
// Seems, it does not work :-(
public class MysqlBootApplication implements CommandLineRunner {

	@Autowired
	DataSource dataSource;


	@Autowired
	private GeoScopeRepository geoScopeDao;


	@Autowired
	private GeoScopeService geoScopeService;
	@Autowired
	private KeyFigureRepository kfDao;


	public static void main(String[] args) {
		SpringApplication.run(MysqlBootApplication.class, args);
	}

	   

	@Override
	public void run(String... args) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<GeoScope>> typeRef =new TypeReference<List<GeoScope>>() {};
		InputStream inputStream= TypeReference.class.getResourceAsStream("/json/geoScope.json");

		
		try {
			List<GeoScope> geoScopes = mapper.readValue(inputStream, typeRef);
		//	geoScopeDao.deleteAll();
		//	geoScopeDao.flush();

		//	geoScopeService.save(geoScopes);
		} catch (Exception e) {
			System.out.println("Unable to save geoscopes" + e.getMessage());
			throw new RuntimeException(e.getCause());
		}
		
			
		//Iterable<KeyFigure> kfs = this.getData();
	//	kfDao.deleteAll();
	//	kfDao.saveAll(kfs);

		System.out.println("DATASOURCE = " + dataSource);

		System.out.println("FINISHED");
	}

    public  Iterable<KeyFigure> getData() {

       GeoScope debrv = geoScopeDao.findByLocationCode("DEBRV");
       GeoScope deham =  geoScopeDao.findByLocationCode("DEHAM");
       GeoScope nlrtm = geoScopeDao.findByLocationCode("NLRTM");
       GeoScope beanr =  geoScopeDao.findByLocationCode("BEANR");
       GeoScope dedus =  geoScopeDao.findByLocationCode("DEDUS");
       GeoScope dusseldorf = geoScopeDao.findByLocationCode("DUSSELDORF");
       GeoScope dedui= geoScopeDao.findByLocationCode("DEDUI");
 
       String transportMode = "TRUCK";
        String eqGroup= "GP";
        String euro= "EUR";
        String eq20= "20";
        String eq40= "40";

        KeyFigure kf1 = new KeyFigure(1,dedus, null, debrv, 0, transportMode,  false, eq20, "RF", new BigDecimal("200.00"), euro, 0, eq40, new Date());
        KeyFigure kf2 = new KeyFigure(2, dedus, null, deham, 0, transportMode,  false, eq20, eqGroup, new BigDecimal("400.35"), euro, 0, eq40, new Date());
        KeyFigure kf3 = new KeyFigure(2, dedus, null, nlrtm, 0, transportMode,  false, eq20, eqGroup, new BigDecimal("600.35"), euro, 0, eq40, new Date());
        KeyFigure kf4 = new KeyFigure(4, dedus, null, beanr, 0, transportMode,  false, eq20, eqGroup, new BigDecimal("800.34"), euro, 0, eq40, new Date());
        KeyFigure kf5 = new KeyFigure(5, dedus, dedui, beanr, 0,  "BARGE",  true, eq20, eqGroup, new BigDecimal("1000.32"), euro, 0, eq40, new Date());
        KeyFigure kf6 = new KeyFigure(6,dedus, null, debrv, 0, transportMode,  false, eq40, "RF", new BigDecimal("900.30"), euro, 0, eq40, new Date());
        KeyFigure kf7 = new KeyFigure(7,dedus, null, deham, 0, transportMode,  false, eq40, eqGroup, new BigDecimal("1100.30"), euro, 0, eq40, new Date());
        KeyFigure kf8 = new KeyFigure(8,dedus, null, nlrtm, 0, transportMode,  false, eq40, eqGroup, new BigDecimal("1300.35"), euro, 0, eq40, new Date());
        KeyFigure kf9 = new KeyFigure(9,dedus, null, beanr, 0, transportMode,  false, eq40, eqGroup, new BigDecimal("1500.30"), euro, 0, eq40, new Date());
        KeyFigure kf10 = new KeyFigure(10,dedus, dedui, deham, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1700.30"), euro, 0, eq40, new Date());
        KeyFigure kf110 = new KeyFigure(11,dedus, dedui, deham, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1300.30"), euro, 0, eq40, new Date());
        KeyFigure kf111 = new KeyFigure(12,dedus, dedui, beanr, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1400.30"), euro, 0, eq40, new Date());
        KeyFigure kf112 = new KeyFigure(13,dedus, dedui, nlrtm, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1500.30"), euro, 0, eq40, new Date());
        KeyFigure kf113 = new KeyFigure(14,dedus, dedui, debrv, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1600.30"), euro, 0, eq40, new Date());
        KeyFigure kf114= new KeyFigure(15,dedus, dedui, deham, 0, "RAIL",  true, eq40, eqGroup, new BigDecimal("1000.30"), euro, 0, eq40, new Date());
        KeyFigure kf115 = new KeyFigure(16,dedus, dedui, deham, 0, "BARGE",  true, eq40, eqGroup, new BigDecimal("700.30"), euro, 0, eq40, new Date());

        KeyFigure kf11 = new KeyFigure(17,dusseldorf, null, debrv, 0, transportMode,  false, eq20, "RF", new BigDecimal("200.00"), euro, 0, eq40, new Date());
        KeyFigure kf12 = new KeyFigure(18,dusseldorf, null, deham, 0, transportMode,  false, eq20, eqGroup, new BigDecimal("400.35"), euro, 0, eq40, new Date());
        KeyFigure kf13 = new KeyFigure(19,dusseldorf, null, nlrtm, 0, transportMode,  false, eq20, eqGroup, new BigDecimal("600.35"), euro, 0, eq40, new Date());
        KeyFigure kf14 = new KeyFigure(20,dusseldorf, null, beanr, 0, transportMode,  false, eq20, eqGroup, new BigDecimal("800.34"), euro, 0, eq40, new Date());
        KeyFigure kf15 = new KeyFigure(21,dusseldorf, dedui, beanr, 0, "BARGE", true, eq20, eqGroup, new BigDecimal("1000.32"), euro, 0, eq40, new Date());
        KeyFigure kf16 = new KeyFigure(22,dusseldorf, null, debrv, 0, transportMode,  false, eq40, "RF", new BigDecimal("900.30"), euro, 0, eq40, new Date());
        KeyFigure kf17 = new KeyFigure(23,dusseldorf, null, deham, 0, transportMode,  false, eq40, eqGroup, new BigDecimal("1100.30"), euro, 0, eq40, new Date());
        KeyFigure kf18 = new KeyFigure(24,dusseldorf, null, nlrtm, 0, transportMode,  false, eq40, eqGroup, new BigDecimal("1300.35"), euro, 0, eq40, new Date());
        KeyFigure kf19 = new KeyFigure(25,dusseldorf, null, beanr, 0, transportMode,  false, eq40, eqGroup, new BigDecimal("1500.30"), euro, 0, eq40, new Date());
        KeyFigure kf20 = new KeyFigure(26,dusseldorf, dedui, deham, 0, "RAIL/ROAD", true, eq40, eqGroup, new BigDecimal("1700.30"), euro, 0, eq40, new Date());
        KeyFigure kf210 = new KeyFigure(27,dusseldorf, dedui, deham, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1300.30"), euro, 0, eq40, new Date());
        KeyFigure kf211 = new KeyFigure(28,dusseldorf, dedui, beanr, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1400.30"), euro, 0, eq40, new Date());
        KeyFigure kf212 = new KeyFigure(29,dusseldorf, dedui, nlrtm, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1500.30"), euro, 0, eq40, new Date());
        KeyFigure kf213 = new KeyFigure(30,dusseldorf, dedui, debrv, 0, "RAIL/ROAD",  true, eq40, eqGroup, new BigDecimal("1600.30"), euro, 0, eq40, new Date());
        KeyFigure kf214= new KeyFigure(31,dusseldorf, dedui, deham, 0, "RAIL",  true, eq40, eqGroup, new BigDecimal("1000.30"), euro, 0, eq40, new Date());
        KeyFigure kf215 = new KeyFigure(32,dusseldorf, dedui, deham, 0, "BARGE",  true, eq40, eqGroup, new BigDecimal("700.30"), euro, 0, eq40, new Date());
 
        return Arrays.asList(kf1, kf2, kf3, kf4, kf5, kf6, kf7, kf8, kf9, kf10, kf110,kf111,kf112,kf113,kf114,kf115,kf11, kf12, kf13, kf14, kf15, kf16, kf17, kf18, kf19, kf20, kf210,kf211,kf212,kf213,kf214,kf215);
    }
}