package com.damian.figinski.dentistapp;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Wyłącz CSRF, aby uniknąć błędów 403
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());  // Zezwól na wszystkie żądania bez autoryzacji

        return http.build();
    }
}
