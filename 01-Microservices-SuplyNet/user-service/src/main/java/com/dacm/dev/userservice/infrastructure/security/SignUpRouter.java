package com.dacm.dev.userservice.infrastructure.security;

import com.dacm.dev.userservice.infrastructure.adapters.input.Handler.SingUpHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Slf4j
@Configuration
public class SignUpRouter {

    private static final String PATH = "registration";

    @Bean
    RouterFunction<ServerResponse> routerRegistration(SingUpHandler singUpHandler) {
        return RouterFunctions.route()
                .POST(PATH, singUpHandler::save)
                .build();
    }
}
