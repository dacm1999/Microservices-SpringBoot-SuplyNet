package com.dacm.dev.authorizationservice.domain.model;

import java.util.List;

public record UserModel(
        String username,
        String password,
        List<String> roles){
}
