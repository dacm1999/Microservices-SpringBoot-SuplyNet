package com.dacm.dev.authorizationservice.infrastructure.adapters.input.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class AppController {

    @GetMapping("/authorize")
    public Map<String, String> authorized(@RequestParam String code){
        return Collections.singletonMap("token", code);
    }
}
