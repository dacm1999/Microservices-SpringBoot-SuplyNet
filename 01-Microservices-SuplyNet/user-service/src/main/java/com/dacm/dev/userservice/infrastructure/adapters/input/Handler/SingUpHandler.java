package com.dacm.dev.userservice.infrastructure.adapters.input.Handler;

import com.dacm.dev.userservice.application.service.UserService;
import com.dacm.dev.userservice.domain.Message;
import com.dacm.dev.userservice.domain.model.UserModel;
import com.dacm.dev.userservice.infrastructure.config.ObjectValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SingUpHandler {

    private final UserService userService;
    private final ObjectValidator objectValidator;

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.USER_SAVE_SUCCESSFULLY),
            @ApiResponse(responseCode = "400", description = Message.USER_EMAIL_ALREADY_EXISTS),
            @ApiResponse(responseCode = "400", description = Message.USERNAME_TAKEN)
    })
    @ResponseStatus(HttpStatus.CREATED)
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
