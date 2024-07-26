package com.dacm.dev.userservice.infrastructure.security;
import com.dacm.dev.userservice.domain.dtos.requests.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


//import com.dacm.dev.userservice.application.service.UserDetailsService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
@Component
@RequiredArgsConstructor
@Slf4j
public class UserReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final WebClient userServiceWebClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // Consulta al User Service para obtener los detalles del usuario
        return userServiceWebClient.post()
                .uri("/users/authenticate")
                .body(BodyInserters.fromValue(new AuthenticationRequest(username, password)))
                .retrieve()
                .bodyToMono(UserDetails.class)
                .map(userDetails -> {
                    // Aquí puedes comparar las contraseñas y construir un objeto Authentication
                    if (passwordEncoder.matches(password, userDetails.getPassword())) {
                        return new UsernamePasswordAuthenticationToken(
                                userDetails,
                                userDetails.getPassword(),
                                userDetails.getAuthorities()
                        );
                    } else {
                        throw new BadCredentialsException("Invalid credentials");
                    }
                });
    }

    //
//    private final PasswordEncoder passwordEncoder;
//    private final UserDetailsService userDetailsService;
//
//    @Override
//    public Mono<Authentication> authenticate(Authentication authentication) {
//        return null;
//    }
//
//    @Bean
//    @Primary
//    protected ReactiveAuthenticationManager reactiveAuthenticationManager() {
//
//        return authentication -> {
//            userDetailsService.findByUsername( authentication.getPrincipal().toString() )
//
//                    .switchIfEmpty( Mono.error( new UsernameNotFoundException("User not found")))
//
//                    .flatMap(user->{
//
//                        final String username           =   authentication.getPrincipal().toString();
//                        final CharSequence rawPassword  =   authentication.getCredentials().toString();
//
//                        if( passwordEncoder.matches(rawPassword, user.getPassword())){
//
//                            log.info("User has been authenticated {}", username);
//                            return Mono.just( new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities()) );
//                        }
//
//                        //This constructor can be safely used by any code that wishes to create a UsernamePasswordAuthenticationToken, as the isAuthenticated() will return false.
//                        return Mono.just( new UsernamePasswordAuthenticationToken(username, authentication.getCredentials()) );
//                    });
//            return null;
//        };
//    }
//

}
