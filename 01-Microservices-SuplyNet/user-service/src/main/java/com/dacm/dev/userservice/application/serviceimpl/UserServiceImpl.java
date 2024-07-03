package com.dacm.dev.userservice.application.serviceimpl;

import com.dacm.dev.userservice.application.service.UserService;
import com.dacm.dev.userservice.domain.Message;
import com.dacm.dev.userservice.domain.dtos.response.ApiResponseDto;
import com.dacm.dev.userservice.domain.dtos.response.UserDto;
import com.dacm.dev.userservice.domain.model.UserModel;
import com.dacm.dev.userservice.infrastructure.adapters.input.mapper.UserMapper;
import com.dacm.dev.userservice.infrastructure.adapters.output.persistence.entity.User;
import com.dacm.dev.userservice.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Mono<UserDto> save(UserModel userModel) {
        return userRepository.findByUsername(userModel.getUsername())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
//                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,Message.USERNAME_TAKEN, LocalDateTime.now()));
                        return Mono.error(new Exception(Message.USERNAME_TAKEN));
                    } else {
                        User userEntity = User.builder()
                                .username(userModel.getUsername())
                                .password(passwordEncoder.encode(userModel.getPassword()))
                                .firstName(userModel.getFirstName())
                                .lastName(userModel.getLastName())
                                .email(userModel.getEmail())
                                .expired(false)
                                .credentialsExpired(false)
                                .locked(false)
                                .disabled(false)
                                .build();
                        return userRepository.save(userEntity)
                                .map(UserMapper::entityToDto);
                    }
                });
    }

    @Override
    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll()
                .map(UserMapper::entityToDto);
    }

    @Override
    public Mono<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::entityToDto)
                .switchIfEmpty(Mono.error(new Exception(Message.USER_NOT_FOUND)));
    }

    @Override
    public Mono<ApiResponseDto> updateUser(String username, UserModel userModel) {
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> {
                    if (userModel.getUsername() != null) {
                        existingUser.setUsername(userModel.getUsername());
                    }
                    if (userModel.getEmail() != null) {
                        existingUser.setEmail(userModel.getEmail());
                    }
                    if (userModel.getFirstName() != null) {
                        existingUser.setFirstName(userModel.getFirstName());
                    }
                    if (userModel.getLastName() != null) {
                        existingUser.setLastName(userModel.getLastName());
                    }

                    return userRepository.save(existingUser)
                            .map(UserMapper::entityToDto)
                            .map(userDto -> new ApiResponseDto(
                                    200,
                                    Message.USER_UPDATE_SUCCESSFULLY,
                                    HttpStatus.OK,
                                    LocalDateTime.now(),
                                    userDto
                            ));
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.USER_NOT_FOUND)));
//                .switchIfEmpty(Mono.error(new Exception(Message.USER_NOT_FOUND)));
    }

    @Override
    public Mono<ApiResponseDto> deleteUser(String username) {
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    Mono<UserDto> userDtoMono = Mono.just(UserMapper.entityToDto(user));
                    return userRepository.deleteByUsername(username)
                            .then(userDtoMono.map(userDto -> new ApiResponseDto(
                                    200,
                                    Message.USER_DELETE_SUCCESSFULLY,
                                    HttpStatus.OK,
                                    LocalDateTime.now(),
                                    userDto
                            )));
                })
                .switchIfEmpty(Mono.error(new Exception(Message.ERROR)));
    }


}
