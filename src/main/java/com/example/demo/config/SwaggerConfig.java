package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;

@Configuration
public class SwaggerConfig {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo())
//                .securitySchemes(Arrays.asList(apiKey()));
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("REST API")
//                .description("The REST API for demo swagger.").termsOfServiceUrl("")
//                .contact(new Contact("RICH LEE", "", "rich.lee@gmail.com"))
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//                .version("0.0.1")
//                .build();
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("authkey", "Authorization", "header");
//    }

}
