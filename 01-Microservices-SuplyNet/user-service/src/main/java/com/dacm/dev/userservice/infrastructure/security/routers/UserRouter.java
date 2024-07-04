package com.dacm.dev.userservice.infrastructure.security.routers;

import com.dacm.dev.userservice.domain.Message;
import com.dacm.dev.userservice.domain.dtos.response.ApiResponseDto;
import com.dacm.dev.userservice.domain.dtos.response.UserDto;
import com.dacm.dev.userservice.domain.exceptions.CustomAttributes;
import com.dacm.dev.userservice.domain.exceptions.CustomException;
import com.dacm.dev.userservice.infrastructure.adapters.input.Handler.UserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
public class UserRouter {

    @Autowired
    private UserHandler handler;

    private static final String USERS_PATH = "/users";


    @Bean
    @RouterOperations({

            @RouterOperation(path = USERS_PATH + "/all", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "getAll",
                    operation = @Operation(operationId = "getAll", summary = "Get All users", responses = {
                            @ApiResponse(responseCode = "200", description = Message.SUCCESS, content =
                            @Content(schema = @Schema(implementation = UserDto.class)))
                    })
            ),


            @RouterOperation(path = USERS_PATH + "/user-info/{username}", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "getUserByUsername",
                    operation = @Operation(operationId = "getUserByUsername", summary = "Get user information by username",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = Message.SUCCESS, content = @Content(schema = @Schema(implementation = UserDto.class))),
                                    @ApiResponse(responseCode = "404", description = Message.USER_NOT_FOUND, content = @Content(schema = @Schema(implementation = CustomException.class)))
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "username", required = true, description = "Username of the user to retrieve")
                            })
            ),


            @RouterOperation(path = USERS_PATH + "/update/{username}", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT, beanClass = UserHandler.class, beanMethod = "update",
                    operation = @Operation(operationId = "update", summary = "Update user information",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = Message.USER_UPDATE_SUCCESSFULLY, content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
                                    @ApiResponse(responseCode = "404", description = Message.USER_NOT_FOUND, content = @Content(schema = @Schema(implementation = CustomException.class)))
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "username", required = true, description = "Username of the user to retrieve")
                            })
            ),

            @RouterOperation(path = USERS_PATH + "/delete/{username}", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = UserHandler.class, beanMethod = "delete",
                    operation = @Operation(operationId = "delete", summary = "Delete user from database",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = Message.USER_DELETE_SUCCESSFULLY, content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
                                    @ApiResponse(responseCode = "404", description = Message.USER_NOT_FOUND, content = @Content(schema = @Schema(implementation = CustomAttributes.class)))
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "username", required = true, description = "Username of the user to retrieve")
                            })
            )
    })
    public RouterFunction<ServerResponse> routerFunctionA() {
        return RouterFunctions.route()
                .GET(USERS_PATH + "/all", handler::getAll)
                .GET(USERS_PATH + "/user-info/{username}", handler::getUserByUsername)
                .PUT(USERS_PATH + "/update/{username}", handler::update)
                .DELETE(USERS_PATH + "/delete/{username}", handler::delete)
                .build();
    }
}
