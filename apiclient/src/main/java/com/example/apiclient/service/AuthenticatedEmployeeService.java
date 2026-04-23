package com.example.apiclient.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthenticatedEmployeeService {

    private final WebClient authenticatedWebClient;

    public AuthenticatedEmployeeService(
            @Qualifier("authenticatedWebClient") WebClient authenticatedWebClient) {
        this.authenticatedWebClient = authenticatedWebClient;
    }

    public String ping() {
        return authenticatedWebClient.get()
                .uri("/api/auth-required")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}