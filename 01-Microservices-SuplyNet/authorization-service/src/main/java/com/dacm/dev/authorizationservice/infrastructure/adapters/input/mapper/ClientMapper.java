package com.dacm.dev.authorizationservice.infrastructure.adapters.input.mapper;

import com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.entity.Client;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Date;


public class ClientMapper {

    public static RegisteredClient toRegisteredClient(Client client){

        RegisteredClient registeredClient = RegisteredClient.withId(client.getClientId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientIdIssuedAt(new Date(System.currentTimeMillis()).toInstant())
                .clientAuthenticationMethods(clientAuthMethods -> {
                    client.getClientAuthenticationMethods().stream()
                            .map(method -> new ClientAuthenticationMethod(method))
//                            .forEach(each -> clientAuthMethods.add(each));
                            .forEach(clientAuthMethods::add);
                })
                .authorizationGrantTypes(authGrantTypes -> {
                    client.getAuthorizationGrantTypes().stream()
                            .map(grantType -> new AuthorizationGrantType(grantType))
                            .forEach(authGrantTypes::add);
                })
                .redirectUris(redirectUris ->
                        client.getRedirectUris().stream().forEach(redirectUris::add))
                .scopes(scopes -> client.getScopes().stream().forEach(scopes::add))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(client.getDurationInMinutes()))
                        .refreshTokenTimeToLive(Duration.ofMinutes(client.getDurationInMinutes() * 4))
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(client.isRequiredProofKey())
                        .build())
                .build();
        return registeredClient;
    }
}
