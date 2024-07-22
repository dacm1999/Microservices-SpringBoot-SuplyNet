package com.dacm.dev.authorizationservice.infrastructure.adapters.input.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@Tag(name = "User Operations", description = "Endpoints for managing users")
public class AppController {


    @GetMapping("/authorize")
    @Operation(summary = "Authorize user", description = "This endpoint returns a token for a given authorization code.")
    public Map<String, String> authorized(@RequestParam String code){
        return Collections.singletonMap("token", code);
    }
}
