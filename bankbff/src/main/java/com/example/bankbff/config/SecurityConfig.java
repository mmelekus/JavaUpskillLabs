package com.example.bankbff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Security configuration for the BFF.
 *
 * Configures Spring Security to:
 *  - Require authentication for /api/** endpoints.
 *  - Allow the auto-exposed OAuth endpoints (/oauth2/**, /login/**, /logout) through
 *    without explicit configuration. Spring Security registers these
 *    automatically when spring-boot-starter-oauth2-client is on the classpath.
 *  - Return 401 (instead of redirecting) for API requests that ask for JSON.
 *  - Disable CSRF for this lab (a deliberate simplification; we will turn it
 *    on in Lab 4.7 when the React SPA needs it).
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF protection is disabled for Lab 4.6 for simplicity.
                // Lab 4.7 will turn it on with a cookie-based token repository
                // appropriate for the React SPA.
                .csrf(csrf -> {
                    CsrfTokenRequestAttributeHandler csrfHandler = new CsrfTokenRequestAttributeHandler();
                    csrfHandler.setCsrfRequestAttributeName(null);

                    csrf
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                            .csrfTokenRequestHandler(csrfHandler);
                })
        // TODO 4.1: Configure authorization rules.
        //
        // Use .authorizeHttpRequests() to declare:
        //   - /oauth2/** is public (the OAuth flow starts here)
        //   - /login/** is public (the OAuth callback lands here)
        //   - /error and / are public
        //   - everything else requires authentication (.authenticated())
        //
        // Pattern:
        //   .authorizeHttpRequests(auth -> auth
        //       .requestMatchers("/oauth2/**", "/login/**", "/error", "/").permitAll()
        //       .anyRequest().authenticated())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/**", "/login/**", "/error", "/").permitAll()
                        .anyRequest().authenticated())


        // TODO 4.2: Configure the dual-audience authentication entry point.
        //
        // For requests that ask for JSON (Accept: application/json),
        // return a clean 401 instead of redirecting to the login page.
        // Browser navigation requests (Accept: text/html) will still
        // be redirected to the login page by oauth2Login() below.
        //
        // Pattern:
        //   .exceptionHandling(ex -> {
        //       RequestMatcher apiRequest =
        //           new MediaTypeRequestMatcher(MediaType.APPLICATION_JSON);
        //       ex.defaultAuthenticationEntryPointFor(
        //               new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
        //               apiRequest);
        //   })
                .exceptionHandling(ex -> {
                    RequestMatcher apiRequest =
                            new MediaTypeRequestMatcher(MediaType.APPLICATION_JSON);
                    ex.defaultAuthenticationEntryPointFor(
                            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                            apiRequest);
                })


        // TODO 4.3: Enable OAuth2 login with default settings.
        //
        // Pattern:
        //   .oauth2Login(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())

        // TODO 4.4: Enable logout with default settings.
        //
        // Pattern:
        //   .logout(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutSuccessHandler(oidcLogoutSuccessHandler(
                                http.getSharedObject(ClientRegistrationRepository.class))));        ;

        return http.build();
    }

    @Bean
    public LogoutSuccessHandler oidcLogoutSuccessHandler(
            ClientRegistrationRepository clientRegistrationRepository) {

        OidcClientInitiatedLogoutSuccessHandler handler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);

        // After the auth server completes logout, send the browser back here.
        // Must match a postLogoutRedirectUri registered on the auth server.
        handler.setPostLogoutRedirectUri("http://localhost:5173/");

        return handler;
    }
}