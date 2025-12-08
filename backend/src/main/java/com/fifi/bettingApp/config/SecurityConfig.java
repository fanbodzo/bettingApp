package com.fifi.bettingApp.config;

import com.fifi.bettingApp.security.AuthTokenFilter;
import com.fifi.bettingApp.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//znowu on potrzeby do wstrzykiwania zeby nie pisac wszedzei konstruktora
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    //wstrzykniecie filtru tokenu
    private final AuthTokenFilter authTokenFilter;

    //bean do definicji glownego lancucha filtrow bezpieczenstwa
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                //mowimy springowi zeby dzialal w trybie stateless daje topewnosc ze spring nie bedzie tworzyc
                //sesji po stronie serwera i jej uzywal , cale uwierzytelnianie opiera sie na toknie jwt
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //kazdy token ma dostep do podanych endpointow
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users").permitAll()
                .anyRequest().authenticated());
        //wstawiamy walsnego straznika przed sprawdzniem loginu ihasla i wstawiam tam moj walsny authFilter
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

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
