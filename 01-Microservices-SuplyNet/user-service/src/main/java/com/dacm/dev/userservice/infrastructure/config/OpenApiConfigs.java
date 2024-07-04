package com.dacm.dev.userservice.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition
@Configuration
public class OpenApiConfigs {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service Api")
                        .version("1.0.0")
                        .description("API for user service application")
                        .contact(new Contact().name("Daniel Contreras").email("dacm.dev@icloud.com").url("https://dacm-dev.netlify.app/"))
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .components(
                        new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

}
