package com.eki.shipment.run;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.eki.config.ShipmentContextConfig;
import com.eki.config.ShipmentPersistenceJpaConfig;
import com.eki.shipment.model.GeoScope;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @SpringBootApplication makes @EnableWebMvc obsolete.
 * 
 * This annotation adds the following other annotations:
 *
    @Configuration – which marks the class as a source of bean definitions
    @EnableAutoConfiguration – which tells the framework to add beans based on the dependencies on the classpath automatically
    @ComponentScan – which scans for other configurations and beans in the same package as the Application class or below

 */
@SpringBootApplication
// Needed, otherwise tests will aLL fail!
@ComponentScan("com.eki")
public class MysqlBootApplication implements CommandLineRunner {
	Logger logger = LoggerFactory.getLogger(MysqlBootApplication.class);

    @SuppressWarnings("rawtypes")
	private final static Class[] CONFIGS = { // @formatter:off
            ShipmentContextConfig.class,
            ShipmentPersistenceJpaConfig.class,
            MysqlBootApplication.class
    }; // @formatter:on

	public static void main(String[] args) {
	    final SpringApplication springApplication = new SpringApplication(CONFIGS);
        springApplication.addInitializers(new MyApplicationContextInitializer());
        springApplication.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<GeoScope>> typeRef = new TypeReference<List<GeoScope>>() {
		};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/geoScope.json");

		try {
			List<GeoScope> geoScopes = mapper.readValue(inputStream, typeRef);
			// geoScopeDao.deleteAll();
			// geoScopeDao.flush();

			// geoScopeService.save(geoScopes);
		} catch (Exception e) {
			logger.error("Unable to save geoscopes" + e.getMessage());
			throw new RuntimeException(e.getCause());
		}

		// Iterable<KeyFigure> kfs = this.getData();
		// kfDao.deleteAll();
		// kfDao.saveAll(kfs);

		// Iterable<OceanRoute> oceanRoutes = OceanRouteData.getOceanRoutes();
		// oceanRouteDao.deleteAll();
		// oceanRouteDao.saveAll(oceanRoutes);
		logger.info("Boot process FINISHED");
	}


}