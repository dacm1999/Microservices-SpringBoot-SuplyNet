package com.dacm.dev.userservice.infrastructure.adapters.input.Handler;


import com.dacm.dev.userservice.application.service.UserService;
import com.dacm.dev.userservice.domain.Message;
import com.dacm.dev.userservice.domain.dtos.response.ApiResponseDto;
import com.dacm.dev.userservice.domain.dtos.response.UserDto;
import com.dacm.dev.userservice.domain.model.UserModel;
import com.dacm.dev.userservice.infrastructure.adapters.output.persistence.entity.User;
import com.dacm.dev.userservice.infrastructure.config.ObjectValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@Tag(name = "User Operations", description = "Endpoints for managing users")
public class UserHandler {

    private final UserService userService;
    private final ObjectValidator objectValidator;


    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<UserDto> userFlux = userService.getAllUsers();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userFlux, User.class);
    }



    public Mono<ServerResponse> getUserByUsername(ServerRequest request) {
        String username = request.pathVariable("username");
        Mono<UserDto> user = userService.getUserByUsername(username);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(user, UserDto.class);
    }


    public Mono<ServerResponse> update(ServerRequest request) {
        String username = request.pathVariable("username");
        Mono<UserModel> userModelMono = request.bodyToMono(UserModel.class)
                .doOnNext(objectValidator::validate);
        return userModelMono.flatMap(userDto1 -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userService.updateUser(username, userDto1), UserDto.class));
    }


    public Mono<ServerResponse> delete(ServerRequest request) {
        String username = request.pathVariable("username");
        return userService.deleteUser(username)
                .flatMap(apiResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(apiResponse)
                )
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new ApiResponseDto(
                                HttpStatus.NOT_FOUND.value(),
                                Message.USER_NOT_FOUND,
                                HttpStatus.NOT_FOUND,
                                LocalDateTime.now()
                        ))
                )
                .onErrorResume(e -> ServerResponse.status(HttpStatus.FORBIDDEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new ApiResponseDto(
                                HttpStatus.FORBIDDEN.value(),
                                "Access Denied",
                                HttpStatus.FORBIDDEN,
                                LocalDateTime.now()
                        ))
                );
    }


}

