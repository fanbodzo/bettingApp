package com.fifi.bettingApp.config;

import com.fifi.bettingApp.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//znowu on potrzeby do wstrzykiwania zeby nie pisac wszedzei konstruktora
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    //bean do definicji glownego lancucha filtrow bezpieczenstwa
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());

        return http.build();
    }

    /**
     * beand definiujacy endkoder hasel uzywam gotowego bCrypt encodera
     * bedzie mozna to wstrzykiwac np w rejestracji
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * bean ktory jest managerem Autentykacji (nie autoryzacji wazneeee)
     * uzywa on AuthenticationConfiguration do automatycznego skonfigurowania sie
     * z odpowiednim UserDetailsService i PasswordEncoder ktore mamy zdefinioowane jako beany
     * bedzie touzywane kontrolerze podczas logowania
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
