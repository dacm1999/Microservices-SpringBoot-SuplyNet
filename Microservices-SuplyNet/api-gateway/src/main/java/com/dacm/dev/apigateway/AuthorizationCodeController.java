package com.dacm.dev.apigateway;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class AuthorizationCodeController {

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code) {
        // Aquí puedes obtener el token usando el código de autorización
        String code1 = obtainTokenFromAuthorizationCode(code);
        return Collections.singletonMap("authorization code:", code);
    }

    private String obtainTokenFromAuthorizationCode(String code) {
        // Lógica para obtener el token usando el código de autorización
        // Este método debe realizar una solicitud al auth-server para intercambiar el código por un token
        return "fake-token-for-demo";
    }
}
