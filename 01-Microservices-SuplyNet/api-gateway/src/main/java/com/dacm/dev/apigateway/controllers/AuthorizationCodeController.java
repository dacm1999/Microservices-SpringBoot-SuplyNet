package com.dacm.dev.apigateway;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class AuthorizationCodeController {

    @Operation(summary = "Retrieve authorization code to get Access Token")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "authorization code"),
            }
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code) {
        // Aquí puedes obtener el token usando el código de autorización
        String code1 = obtainTokenFromAuthorizationCode(code);
        return Collections.singletonMap("authorization code:", code);
    }

    private String obtainTokenFromAuthorizationCode(String code) {
        return "fake-token-for-demo";
    }
}
