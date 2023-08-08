package com.library.app.security;

import com.library.app.repositories.types.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .requestMatchers("/api/v1/library/registration").permitAll()
                        .requestMatchers("/api/v1/library/actions/admin/**").hasRole(Role.SUPER_ADMIN.name())
                        .requestMatchers("/api/v1/library/admin/**").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .oauth2Login(AbstractAuthenticationFilterConfigurer::permitAll)
                .rememberMe(Customizer.withDefaults());
        return http.build();
    }
}
