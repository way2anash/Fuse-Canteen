package com.fusecanteen.utils;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Configuration {

	@Bean
	public Docket swaggerConfiguration() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
//				.paths(PathSelectors.ant("/api/*"))
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.fusecanteen"))
				.build()
				.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		
		return new ApiInfo(
				
				"Food Canteen API",
				"Sample API for Food Canteen Management",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Anash Ansari", "https://github.com/way2anash",
						"ansarianash.np@gmail.com"),
				"API License",
				"https://github.com/way2anash",
				Collections.emptyList());
	}
	
}
