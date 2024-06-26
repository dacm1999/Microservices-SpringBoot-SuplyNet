package com.dacm.dev.authorizationservice.application.serviceimpl;

import com.dacm.dev.authorizationservice.application.service.ClientService;
import com.dacm.dev.authorizationservice.domain.Message;
import com.dacm.dev.authorizationservice.domain.exceptions.ObjectNotFoundException;
import com.dacm.dev.authorizationservice.infrastructure.adapters.input.mapper.ClientMapper;
import com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.entity.Client;
import com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    Map<String, RegisteredClient> clients = new HashMap<>();

    @Override
    public void save(RegisteredClient registeredClient) {
        clients.put(registeredClient.getClientId(), registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findByClientId(id)
                .orElseThrow(() -> new ObjectNotFoundException(Message.CLIENT_NOT_FOUND));

        return ClientMapper.toRegisteredClient(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return this.findById(clientId);
    }
}
