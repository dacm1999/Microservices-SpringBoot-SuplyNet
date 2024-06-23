package com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.repository;

import com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByClientId(String clientId);
}
