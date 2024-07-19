package com.dacm.dev.authorizationservice.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
public class ProfilesConfig {

    @Value("${application.message}")
    private String message;


    @Component
    @Profile(value = "docker")
    class RunnerOne implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            System.out.println(message);
        }
    }

    @Component
    @Profile(value = "local")
    class RunnerTwo implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            System.out.println(message);
        }
    }
}
