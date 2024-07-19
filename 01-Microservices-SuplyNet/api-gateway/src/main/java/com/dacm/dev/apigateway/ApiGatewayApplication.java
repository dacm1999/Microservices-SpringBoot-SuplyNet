package com.dacm.dev.apigateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class ApiGatewayApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }


//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder
//                .routes()
//                .route(r -> r.path("/user-service/v3/api-docs").and()
//                        .method(HttpMethod.GET).uri("lb://user-service"))
//                .build();
//    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("authorization-service", r -> r.path("/oauth2/authorize", "/oauth2/token", "/userinfo", "/.well-known/jwks.json", "/authorization-service/v3/api-docs")
                        .uri("lb://authorization-service"))
                .route("user-service", r -> r.path("/user-service/v3/api-docs")
                        .and()
                        .method(HttpMethod.GET)
                        .uri("lb://user-service"))
                .build();
    }



}
