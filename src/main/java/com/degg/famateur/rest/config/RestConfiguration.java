package com.degg.famateur.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class RestConfiguration {



    /**
     * Create basic configuration for Swagger2 documentation
     * @return basic configuration for Swagger2 documentation
     */
    @Bean
    public Docket swaggerConfiguration() {

        // Return a prepared Docket instance
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/v1/**"))
                .apis(RequestHandlerSelectors.basePackage("com.degg.famateur.rest"))
                .build()
                .apiInfo(getApiInfo());
    }




    /**
     * Create API Info for Swagger2 documentation
     * @return API Info for Swagger2 documentation
     */
    private ApiInfo getApiInfo() {
        return new ApiInfo(
            "Famateur Resorts Management API",
            "REST API for Famateur Resorts management",
            "1.0",
            "Free to use",
            new Contact("Damian Garcia", "https://github.com/dexequiel87", "dexequiel.garcia@gmail.com"),
            "API License",
            "https://github.com/dexequiel87",
            Collections.emptyList());
    }

}
