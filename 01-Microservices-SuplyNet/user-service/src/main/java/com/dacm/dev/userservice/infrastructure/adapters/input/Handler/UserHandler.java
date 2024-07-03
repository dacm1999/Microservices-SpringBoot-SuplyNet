package com.dacm.dev.userservice.infrastructure.adapters.input.Handler;


import com.dacm.dev.userservice.application.service.UserService;
import com.dacm.dev.userservice.domain.Message;
import com.dacm.dev.userservice.domain.dtos.response.ApiResponseDto;
import com.dacm.dev.userservice.domain.dtos.response.UserDto;
import com.dacm.dev.userservice.domain.model.UserModel;
import com.dacm.dev.userservice.infrastructure.adapters.output.persistence.entity.User;
import com.dacm.dev.userservice.infrastructure.config.ObjectValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final ObjectValidator objectValidator;

    @Operation(summary = "Retrieve all users from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.SUCCESS),
            @ApiResponse(responseCode = "200", description = Message.SUCCESS)
    })
    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<UserDto> userFlux = userService.getAllUsers();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userFlux, User.class);
    }

    @Operation(summary = "Find user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.USER_SAVE_SUCCESSFULLY),
            @ApiResponse(responseCode = "400", description = Message.USER_NOT_FOUND)
    })
    @ResponseStatus(HttpStatus.OK)
    public Mono<ServerResponse> getUserByUsername(ServerRequest request) {
        String username = request.pathVariable("username");
        Mono<UserDto> user = userService.getUserByUsername(username);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(user, UserDto.class);
    }


    @Operation(summary = "Find user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.USER_UPDATE_SUCCESSFULLY),
            @ApiResponse(responseCode = "400", description = Message.USER_NOT_FOUND)
    })
    @ResponseStatus(HttpStatus.OK)
    public Mono<ServerResponse> update(ServerRequest request) {
        String username = request.pathVariable("username");
        Mono<UserModel> userModelMono = request.bodyToMono(UserModel.class)
                .doOnNext(objectValidator::validate);
        return userModelMono.flatMap(userDto1 -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userService.updateUser(username, userDto1), UserDto.class));
    }

    @Operation(summary = "Find user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.USER_DELETE_SUCCESSFULLY),
            @ApiResponse(responseCode = "400", description = Message.USER_NOT_FOUND)
    })
    @ResponseStatus(HttpStatus.OK)
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

