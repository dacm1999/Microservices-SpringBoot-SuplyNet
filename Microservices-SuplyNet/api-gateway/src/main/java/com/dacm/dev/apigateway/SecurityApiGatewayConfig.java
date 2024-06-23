package com.dacm.dev.apigateway;

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

    @Bean
    @Order(1)
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.
                authorizeExchange(authorizeExchange ->
                        authorizeExchange
                                .pathMatchers("/login/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/authorized").permitAll()
                                .pathMatchers(HttpMethod.GET, "/list").hasAuthority("SCOPE_openid")
                                .pathMatchers(HttpMethod.POST, "/create").hasAuthority("SCOPE_openid")
                                .anyExchange().authenticated())
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults());
//                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityWebFilterChain pkceFilterChain(
            ServerHttpSecurity http, ServerOAuth2AuthorizationRequestResolver resolver
    ) {
        http.authorizeExchange(r -> r.anyExchange().authenticated());
        http.oauth2Login(auth -> auth.authorizationRequestResolver(resolver));
        return http.build();
    }

    //    @Bean
//    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(requests -> {
//            requests
//                    .requestMatchers("/login").permitAll();
//
//            requests.anyRequest().authenticated();
//        });
//        http.formLogin(formLoginConfig -> formLoginConfig.loginPage("/login").permitAll());
//
//        return http.build();
//    }

    @Bean
    public ServerOAuth2AuthorizationRequestResolver pkceResolver(ReactiveClientRegistrationRepository repo) {
        var resolver = new DefaultServerOAuth2AuthorizationRequestResolver(repo);
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
        return resolver;
    }
}
