package com.example.apiclient.controller;

import com.example.apiclient.config.StubServerConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RestController
@RequestMapping("/stubs")
public class StubManagementController {

    private final StubServerConfig stubServerConfig;

    public StubManagementController(StubServerConfig stubServerConfig) {
        this.stubServerConfig = stubServerConfig;
    }

    // Simulates the /api/flaky upstream recovering and returning 200
    @PostMapping("/flaky/recover")
    public ResponseEntity<String> makeFlakyRecover() {
        stubServerConfig.getServer().stubFor(
                get(urlEqualTo("/api/flaky"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("[{\"id\":1,\"name\":\"Alice\",\"department\":\"Engineering\"}]"))
        );
        return ResponseEntity.ok("Stub updated: /api/flaky now returns 200");
    }

    // Simulates the /api/flaky upstream going back down
    @PostMapping("/flaky/fail")
    public ResponseEntity<String> makeFlakyFail() {
        stubServerConfig.getServer().stubFor(
                get(urlEqualTo("/api/flaky"))
                        .willReturn(aResponse()
                                .withStatus(503)
                                .withBody("Service Unavailable"))
        );
        return ResponseEntity.ok("Stub updated: /api/flaky now returns 503");
    }
}