package com.example.apiclient.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Configuration
public class StubServerConfig {

    private static final Logger log = LoggerFactory.getLogger(StubServerConfig.class);
    private static final int STUB_PORT = 8081;

    private WireMockServer wireMockServer;

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            return;
        }

        wireMockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig().port(STUB_PORT)
        );
        wireMockServer.start();

        // Point the WireMock static DSL at our server instance
        WireMock.configureFor("localhost", STUB_PORT);

        registerStubs();
        log.info("Stub server started on port {}. Admin UI: http://localhost:{}/__admin/mappings",
                STUB_PORT, STUB_PORT);
    }

    @PreDestroy
    public void stop() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            log.info("Stub server stopped.");
        }
    }

    // Exposed so StubManagementController can update stubs at runtime
    public WireMockServer getServer() {
        return wireMockServer;
    }

    private void registerStubs() {

        // --- Employee list ---
        stubFor(get(urlEqualTo("/api/employees"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[" +
                                "{\"id\":1,\"name\":\"Alice\",\"department\":\"Engineering\"}," +
                                "{\"id\":2,\"name\":\"Bob\",\"department\":\"Finance\"}" +
                                "]")));

        // --- Single employee ---
        stubFor(get(urlEqualTo("/api/employees/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":1,\"name\":\"Alice\",\"department\":\"Engineering\"}")));

        // --- Missing employee (404) ---
        stubFor(get(urlEqualTo("/api/employees/99"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("Not found")));

        // --- Create employee (201 with Location header) ---
        stubFor(post(urlEqualTo("/api/employees"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Location", "http://localhost:8081/api/employees/3")
                        .withBody("{\"id\":3,\"name\":\"Charlie\",\"department\":\"HR\"}")));

        // --- Slow response (used to demonstrate timeout behaviour in Exercise 2) ---
        stubFor(get(urlEqualTo("/api/slow"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(5000)
                        .withBody("finally!")));

        // --- Flaky endpoint (starts as 503; Exercise 3 updates it to 200 at runtime) ---
        stubFor(get(urlEqualTo("/api/flaky"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withBody("Service Unavailable")));

        // --- Auth-required: 401 when Authorization header is missing or wrong ---
        // WireMock matches stubs in reverse registration order (last registered wins).
        // The catch-all 401 stub must be registered FIRST so that the more specific
        // Bearer stub registered below takes priority when the header is present.
        stubFor(get(urlEqualTo("/api/auth-required"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withBody("Unauthorized")));

        // --- Auth-required: 200 when Authorization header contains "Bearer" ---
        // Registered AFTER the catch-all so WireMock evaluates this one first.
        stubFor(get(urlEqualTo("/api/auth-required"))
                .withHeader("Authorization", containing("Bearer"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"Authenticated successfully\"}")));
    }
}