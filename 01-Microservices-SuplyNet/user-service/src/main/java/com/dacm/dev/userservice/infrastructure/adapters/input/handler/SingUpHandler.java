package com.dacm.dev.userservice.infrastructure.adapters.input.handler;

import com.dacm.dev.userservice.application.service.UserService;
import com.dacm.dev.userservice.domain.model.UserModel;
import com.dacm.dev.userservice.infrastructure.config.ObjectValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Tag(name = "Sign Up Operations", description = "Endpoints for user sign up")
public class SingUpHandler {

    private final UserService userService;
    private final ObjectValidator objectValidator;


    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<UserModel> userModel = request
                .bodyToMono(UserModel.class)
                .doOnNext(objectValidator::validate);

        return userModel.flatMap(user ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.save(user), UserModel.class)
        );
    }

}
