package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value(("${application.host}"))
    private String host;

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().
                        title("RESTfull API for application 'Chat'")
                        .version("v1")
                        .description("REST API documentation")
                        .termsOfService("s"))
                .addServersItem(new Server()
                        .url(host + "/")
                        .description("Future Server for application"));
    }

}
