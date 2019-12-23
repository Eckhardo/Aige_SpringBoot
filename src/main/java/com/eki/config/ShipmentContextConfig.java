package com.eki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @Configuration tags the class as a source of bean definitions for the application context.
 * @author eckha
 *
 */


@Configuration
@ImportResource("classpath*:shipmentContextConfig.xml")
@PropertySource({ "classpath:env-${envTarget:dev}.properties" })
public class ShipmentContextConfig {

	

    public ShipmentContextConfig() {
        super();
    }

    // beans

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        return pspc;
    }
}
