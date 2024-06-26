package com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.repository;

import com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername (String username);
}
