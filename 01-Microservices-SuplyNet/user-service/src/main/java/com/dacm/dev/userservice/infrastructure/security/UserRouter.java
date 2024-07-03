package com.dacm.dev.userservice.infrastructure.security;

import com.dacm.dev.userservice.infrastructure.adapters.input.Handler.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
public class UserRouter {

    private static final String USERS_PATH = "users";


    @Bean
    RouterFunction<ServerResponse> routerUser(UserHandler handler) {
        return RouterFunctions.route()
                .GET(USERS_PATH + "/all", handler::getAll)
                .GET(USERS_PATH + "/user-info/{username}", handler::getUserByUsername)
                .PUT(USERS_PATH + "/{username}", handler::update)
                .DELETE(USERS_PATH + "/{username}", handler::delete)
                .build();
    }

}
