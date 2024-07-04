package com.dacm.dev.userservice.infrastructure.security.routers;

import com.dacm.dev.userservice.domain.Message;
import com.dacm.dev.userservice.domain.dtos.response.ApiResponseDto;
import com.dacm.dev.userservice.domain.dtos.response.UserDto;
import com.dacm.dev.userservice.domain.exceptions.CustomAttributes;
import com.dacm.dev.userservice.domain.exceptions.CustomException;
import com.dacm.dev.userservice.infrastructure.adapters.input.Handler.SingUpHandler;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Slf4j
@Configuration
public class SignUpRouter {

    private static final String REGISTRATION_PATH = "/user-service/sign-up";


    @Bean
    @RouterOperations({
            @RouterOperation(path = REGISTRATION_PATH, produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = SingUpHandler.class, beanMethod = "save",
                    operation = @Operation(operationId = "save", summary = "Create new user",
                    responses = {
                            @ApiResponse(responseCode = "200", description = Message.SUCCESS, content = @Content(schema = @Schema(implementation = UserDto.class))),
                            @ApiResponse(responseCode = "400", description = Message.USERNAME_TAKEN, content = @Content(schema = @Schema(implementation = CustomAttributes.class)))
                    })
            ),
    })
    RouterFunction<ServerResponse> routerRegistration(SingUpHandler singUpHandler) {
        return RouterFunctions.route()
                .POST(REGISTRATION_PATH, singUpHandler::save)
                .build();
    }
}
