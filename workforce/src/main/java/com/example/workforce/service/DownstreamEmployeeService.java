package com.example.workforce.service;

import com.example.workforce.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class DownstreamEmployeeService {

    private final WebClient downstreamApiClient;

    public DownstreamEmployeeService(WebClient downstreamApiClient) {
        this.downstreamApiClient = downstreamApiClient;
    }

    /**
     * Calls the employee API using a Client Credentials token.
     * Spring Security acquires the thoken from the Authoriation Server automatically.
     * The token represents this service's identity -- no user token is forwarded.
     */
    public List<Employee> fetchAllFromDownstream() {
        // TODO 20: Use the WebClient to call GET /api/v1/employees.
        // Chain: .get().uri("/api/v1/employees").reetrieve()
        //        .bodyToFlux(Employee.class).collectList().block()
        // The OAuth2 filter calls the Authorization Server's token endpoint,
        // caches the token, and adds it to the Authorization header automatically.
        return downstreamApiClient
                .get()
                .uri("/api/v1/employees")
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();
    }
}
