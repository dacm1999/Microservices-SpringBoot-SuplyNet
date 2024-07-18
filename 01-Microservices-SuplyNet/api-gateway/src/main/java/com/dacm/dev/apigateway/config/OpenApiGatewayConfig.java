package com.dacm.dev.apigateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenApiGatewayConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service Api")
                        .version("1.0.0")
                        .description("API for user service application")
                        .contact(new Contact()
                                .name("Daniel Contreras")
                                .url("https://dacm-dev.netlify.app/"))
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .components(new Components()
                        .addSecuritySchemes("spring_oauth", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .description("OAuth2 flow")
                                .flows(new OAuthFlows()
                                        .clientCredentials(new OAuthFlow()
                                                .tokenUrl("http://localhost:8000/oauth/token")
                                                .scopes(new Scopes()
                                                        .addString("openid", "for read operations")
                                                        .addString("all", "for write operations")
                                                )
                                        )
                                )
                        )
                        .addSecuritySchemes("jwtToken", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                )
                .security(Arrays.asList(
                        new SecurityRequirement().addList("spring_oauth"),
                        new SecurityRequirement().addList("jwtToken")
                ));
    }

}