package com.dacm.dev.userservice.application.service;


import com.dacm.dev.userservice.domain.dtos.response.ApiResponseDto;
import com.dacm.dev.userservice.domain.dtos.response.UserDto;
import com.dacm.dev.userservice.domain.model.UserModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserService {


    Mono<UserDto> save(UserModel userModel);

    Flux<UserDto> getAllUsers();

    Mono<UserDto> getUserByUsername(String username);

    Mono<ApiResponseDto> updateUser(String username, UserModel userModel);

    Mono<ApiResponseDto> deleteUser(String username);


}
