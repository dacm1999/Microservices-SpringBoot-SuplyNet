package com.dacm.dev.apigateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@OpenAPIDefinition(info = @Info(title = "ApiGateway Service", version = "1.0.0", description = "Documentation API Gateway v1.0"))
public class OpenApiGatewayConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
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