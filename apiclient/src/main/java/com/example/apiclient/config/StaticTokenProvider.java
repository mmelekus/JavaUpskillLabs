package com.example.apiclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Provides the bearer token for outbound API calls.
 *
 * In production this would contact an OAuth2 token endpoint,
 * cache the token, and refresh it before expiry.
 * For this exercise it returns a static value injected from configuration.
 */
@Component
public class StaticTokenProvider {

    private final String token;

    // TODO 17: Inject the value of upstream.api.token using @Value.
    // @Value reads from application.yml using Spring's ${...} syntax.
    // Assign the injected value to the token field.
    public StaticTokenProvider(@Value("${upstream.api.token}") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}