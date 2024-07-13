package com.dacm.dev.userservice.infrastructure.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Log4j2
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class UserServiceSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchangeSpec ->
                        authorizeExchangeSpec

//                                .pathMatchers(HttpMethod.GET,"/user-service/webjars/**").permitAll()
//                                .pathMatchers(HttpMethod.GET,"/user-service/swagger-ui.html").permitAll()
//                                .pathMatchers(HttpMethod.GET,"/user-service/swagger-resources/**").permitAll()
//                                .pathMatchers(HttpMethod.GET,"/user-service/v3/api-docs/**").permitAll()
//                                .pathMatchers(HttpMethod.GET,"/user-service/v3/api-docs/**").permitAll()


                                //Registration endpoint
                                .pathMatchers("registration").permitAll()

                                //Users endpoint
                                .pathMatchers(HttpMethod.GET,"users/all").authenticated()
                                .pathMatchers(HttpMethod.GET,"users/user-info/{username}").authenticated()
                                .pathMatchers(HttpMethod.PUT,"users/update/{username}").permitAll()
                                .pathMatchers(HttpMethod.DELETE,"users/{username}").authenticated()
                                .anyExchange()
                                .access(authorizationContextReactiveAuthorizationManager()))
                .exceptionHandling(exceptionHandlingSpec -> {
                    exceptionHandlingSpec
                            .authenticationEntryPoint(serverAuthenticationEntryPoint())
                            .accessDeniedHandler(serverAccessDeniedHandler());
                })
                .oauth2ResourceServer(oAuth2ResourceServerSpec ->
                        oAuth2ResourceServerSpec.jwt(
                                jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())
                        ))
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("permissions");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }


    @Bean
    public ReactiveAuthorizationManager<AuthorizationContext> authorizationContextReactiveAuthorizationManager() {
        return (authentication, object) -> {
            return Mono.just(new AuthorizationDecision(true));
        };
    }

//    @Bean
//    public ServerAccessDeniedHandler serverAccessDeniedHandler() {
//        return (exchange, denied) -> {
//            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//            // Custom error response
//            return exchange.getResponse().writeWith(
//                    Mono.just(exchange.getResponse().bufferFactory().wrap("Access Denied".getBytes()))
//            );
//        };
//    }

    @Bean
    public ServerAccessDeniedHandler serverAccessDeniedHandler() {
        return (exchange, denied) -> {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            String errorMessage = "Access Denied. You do not have permission to access this resource.";
            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(errorMessage.getBytes()))
            );
        };
    }


    @Bean
    public ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
        return new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Service
    public static class MessageService {


        public Mono<String> greet() {
            return ReactiveSecurityContextHolder.getContext()
                    .map(ctx -> {
                        final var who = (JwtAuthenticationToken) ctx.getAuthentication();
                        return "Hello %s! You are granted with %s.".formatted(who.getName(), who.getAuthorities());
                    })
                    .switchIfEmpty(Mono.error(new AuthenticationCredentialsNotFoundException("Security context is empty")));
        }

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public Mono<String> getSecret() {
            return Mono.just("Only authorized personnel can read that");
        }
    }


//    @RestController
    @RequiredArgsConstructor
    public static class GreetingController {
        private final MessageService messageService;

        @GetMapping("/greet")
        public Mono<ResponseEntity<String>> greet() {
            return messageService.greet()
                    .map(ResponseEntity::ok);
        }

        @GetMapping("/secured-route")
        public Mono<ResponseEntity<String>> securedRoute() {
            return messageService.getSecret()
                    .map(ResponseEntity::ok);
        }

        @GetMapping("/secured-method")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public Mono<ResponseEntity<String>> securedMethod() {
            return messageService.getSecret()
                    .map(ResponseEntity::ok);
        }
    }
}
