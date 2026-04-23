package com.example.apiclient.service;

import com.example.apiclient.model.Employee;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ResilientEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(ResilientEmployeeService.class);

    private final WebClient employeeWebClient;

    public ResilientEmployeeService(WebClient employeeWebClient) {
        this.employeeWebClient = employeeWebClient;
    }

    // TODO 15: Add @Retry(name = "upstream-api") to fetchAll().
    // Add @CircuitBreaker(name = "upstream-api", fallbackMethod = "fetchAllFallback") to fetchAll().
    //
    // Annotation ordering matters. CircuitBreaker wraps Retry, which means:
    //   1. The circuit breaker checks its state first.
    //      If open, it fails immediately without making a network call.
    //   2. If closed, Retry orchestrates multiple attempts with exponential backoff.
    //   3. If all retry attempts are exhausted, the exception reaches the circuit breaker,
    //      which counts it as a failure toward the threshold.
    @Retry(name = "upstream-api")
    @CircuitBreaker(name = "upstream-api", fallbackMethod = "fetchAllFallback")
    public List<Employee> fetchAll() {
        log.info("Calling upstream /api/flaky");
        return employeeWebClient.get()
                .uri("/api/flaky")
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();
    }

    // TODO 16: Implement the fallback method.
    // The fallback method must have the same return type as fetchAll() -- List<Employee>.
    // It must accept a Throwable as its last (and only) parameter.
    // Log a warning that includes ex.getMessage() so the failure is observable in logs.
    // Return List.of() to serve a degraded empty response rather than propagating the error.
    public List<Employee> fetchAllFallback(Throwable ex) {
        log.warn("Fallback invoked: {}", ex.getMessage());
        return List.of(); // Replace with your implementation
    }
}