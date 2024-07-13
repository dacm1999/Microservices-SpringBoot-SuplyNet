package com.dacm.dev.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityApiGatewayConfig {

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder
//                .routes()
//                .route(r -> r.path("/user-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://user-service"))
//                .build();
//    }


    @Bean
    @Order(1)
    public SecurityWebFilterChain basicClientFilterChain(ServerHttpSecurity http) {
        http.
                authorizeExchange(authorizeExchange ->
                        authorizeExchange

                                .pathMatchers(HttpMethod.GET,"/webjars/**").permitAll()
                                .pathMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
                                .pathMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
                                .pathMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
                                .pathMatchers(HttpMethod.GET,"/user-service/v3/api-docs/**").permitAll()

                                .pathMatchers("/login/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/authorized").permitAll()
                                .anyExchange().authenticated())
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults());
//                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityWebFilterChain pkceFilterChain(ServerHttpSecurity http, ServerOAuth2AuthorizationRequestResolver resolver) {
        http.authorizeExchange(authorizeExchangeSpec ->
                        authorizeExchangeSpec
                                .pathMatchers("/login").permitAll()
                                .pathMatchers("/logout").authenticated()
                                .anyExchange().authenticated())
                .oauth2Login(oAuth2LoginSpec -> oAuth2LoginSpec.authorizationRequestResolver(resolver))
                .oauth2Client(withDefaults());
        return http.build();
    }


    @Bean
    public ServerOAuth2AuthorizationRequestResolver pkceResolver(ReactiveClientRegistrationRepository repo) {
        var resolver = new DefaultServerOAuth2AuthorizationRequestResolver(repo);
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
        return resolver;
    }
}
