package com.example.banking.config;

import com.example.banking.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // activates @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // TODO 3: Configure authorization rules with authorizeHttpRequests().
                // Rules must be declared in order from most specific to least specific.
                // Required rules:
                //   GET  /health                          permitAll()
                //   GET  /api/v1/accounts/**              hasAuthority("SCOPE_read:accounts")
                //   POST /api/v1/accounts                 hasAuthority("SCOPE_write:accounts")
                //   GET  /api/v1/transactions/**           hasAuthority("SCOPE_read:transactions")
                //   POST /api/v1/transactions             hasAuthority("SCOPE_write:transactions")
                //   GET  /api/v1/users/**                 hasAuthority("SCOPE_admin:users")
                //   anyRequest()                          authenticated()
                //
                // The SCOPE_ prefix is added automatically by Spring Security when it
                // maps the "scope" claim from the JWT to granted authorities.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/health").permitAll()
                        // TODO: add the remaining rules here
                        .requestMatchers(HttpMethod.GET, "/api/v1/accounts").hasAuthority("SCOPE_read:accounts")
                        .requestMatchers(HttpMethod.POST, "/api/va/accounts").hasAuthority("SCOPE_write:accounts")
                        .requestMatchers(HttpMethod.GET, "/api/v1/transactions").hasAuthority("SCOPE_read:transactions")
                        .requestMatchers(HttpMethod.POST, "/api/v1/transactions").hasAuthority("SCOPE_write:transactions")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("SCOPE_admin:users")
                        .anyRequest().authenticated()
                )

        // TODO 4: Configure JWT validation.
        // Use: .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        // Spring Security fetches the public key from jwks-uri, verifies the signature,
        // checks exp/iss/aud, and maps scopes to SCOPE_-prefixed authorities.
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        // TODO 5: Set session management to STATELESS.
        // A REST API using Bearer tokens does not use HTTP sessions.
        // Use: .sessionManagement(session ->
        //          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // TODO 6: Disable CSRF.
        // CSRF attacks depend on browsers sending session cookies automatically.
        // Bearer tokens are not sent automatically, so CSRF does not apply.
        // Use: .csrf(csrf -> csrf.disable())
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            ErrorResponse body = ErrorResponse.of(
                    "ACCESS_DENIED",
                    403,
                    "You do not have permission to perform this action."
            );

            response.getWriter().write(mapper.writeValueAsString(body));
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            ErrorResponse body = ErrorResponse.of(
                    "AUTHENTICATION_REQUIRED",
                    401,
                    "A valid Bearer token is required."
            );

            response.getWriter().write(mapper.writeValueAsString(body));
        };
    }
}