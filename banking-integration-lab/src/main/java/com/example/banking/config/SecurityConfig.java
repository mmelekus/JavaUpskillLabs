package com.example.banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration for the lab application.
 *
 * - All /api/** endpoints require authentication
 * - Unauthenticated requests to /api/** receive 401 (rather than a redirect)
 * - OAuth2 login is the authentication mechanism for non-API requests
 *
 * For the lab tests, the actual OAuth flow is never exercised.
 * Tests use Spring Security's oauth2Login() post-processor to
 * simulate an authenticated user without involving a real auth server.
 *
 * The 401 entry point uses a URL-based matcher (any /api/** path) rather
 * than a media-type matcher. URL-based matching is reliable across
 * environments because it does not depend on the request's Accept header
 * being parsed in a specific way.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/**", "/login/**", "/error").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**")))
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}
