package com.manumb.digital_money_service.business.security;

import com.manumb.digital_money_service.business.users.services.UserServiceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityAuthenticationBeansCreator {

    private final UserServiceHandler userServiceHandler;
    private final PasswordEncoder passwordEncoder;

    public SecurityAuthenticationBeansCreator(UserServiceHandler userServiceHandler, PasswordEncoder passwordEncoder) {
        this.userServiceHandler = userServiceHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userServiceHandler);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
