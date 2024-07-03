package com.dacm.dev.userservice.infrastructure.adapters.output.persistence.repository;

import com.dacm.dev.userservice.domain.dtos.response.UserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.dacm.dev.userservice.infrastructure.adapters.output.persistence.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Mono<User> findByUsername(String username);


    Mono<User> deleteByUsername(String username);

}
