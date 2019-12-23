package com.eki.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!test")
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.eki.controller"))
				.paths(paths())
				.build().apiInfo(apiEndPointsInfo());
	}
	 // Only select apis that matches the given Predicates.
    private Predicate<String> paths() {
    	// Match all paths except /error
        return Predicates.and(
        	PathSelectors.regex("/.*"), 
        	Predicates.not(PathSelectors.regex("/errors.*"))
        );
    }

	private ApiInfo apiEndPointsInfo() {

		return new ApiInfoBuilder().title("Spring Boot REST API")

				.description("Shipment Management REST API")

				.contact(new Contact("Eckhard Kirschning", "www.github.com/Eckhardo", "eckhard.kirschning@freenet.de"))

				.license("Apache 2.0")

				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")

				.version("1.0.0")

				.build();

	}
}