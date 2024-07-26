package com.dacm.dev.userservice.infrastructure.adapters.input.controllers;

import com.dacm.dev.userservice.application.service.UserService;
import com.dacm.dev.userservice.domain.dtos.requests.AuthenticationRequest;
import com.dacm.dev.userservice.infrastructure.adapters.output.persistence.entity.User;
import com.dacm.dev.userservice.infrastructure.adapters.output.persistence.repository.UserRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Authentication controller ", description = "Retrieve user information")
public class UserController {

    private final UserService userService;

    @PostMapping("/authenticate")
    @ApiResponse(responseCode = "200")
    public Mono<ResponseEntity<User>> authenticate(@RequestBody AuthenticationRequest request) {
        return userService.authenticate(request.getUsername(), request.getPassword())
                .map(user -> ResponseEntity.ok().body(user))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }


}
