package com.dacm.dev.authorizationservice.infrastructure.security;

import com.dacm.dev.authorizationservice.domain.Message;
import com.dacm.dev.authorizationservice.domain.exceptions.ObjectNotFoundException;
import com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {


    @Bean
    public PasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder();
    }

}
