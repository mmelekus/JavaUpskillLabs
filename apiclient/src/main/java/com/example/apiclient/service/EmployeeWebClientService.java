package com.example.apiclient.service;

import com.example.apiclient.exception.EmployeeNotFoundException;
import com.example.apiclient.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class EmployeeWebClientService {

    private final WebClient employeeWebClient;

    public EmployeeWebClientService(WebClient employeeWebClient) {
        this.employeeWebClient = employeeWebClient;
    }

    // TODO 9: Implement fetchAll() using WebClient.
    // Chain: .get().uri("/api/employees").retrieve()
    //        .bodyToFlux(Employee.class).collectList().block()
    // bodyToFlux turns the JSON array into a reactive stream of Employee objects.
    // collectList() gathers all emitted items into a single Mono<List<Employee>>.
    // block() subscribes and waits synchronously (acceptable in Spring MVC).
    public List<Employee> fetchAll() {
        return employeeWebClient.get()
                .uri("/api/employees")
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .timeout(Duration.ofSeconds(2))
                .block();
    }

    // TODO 10: Implement fetchById() using URI template variables.
    // Chain: .get().uri("/api/employees/{id}", id).retrieve()
    //        .bodyToMono(Employee.class).block()
    // URI template variables are automatically percent-encoded.
    // They are resistant to path traversal because {id} is treated as a single segment.
    public Employee fetchById(Long id) {
        return employeeWebClient.get()
                .uri("/api/employees/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        response -> Mono.error(new EmployeeNotFoundException(id))
                )
                .bodyToMono(Employee.class)
                .block();
    }

    // TODO 11: Implement createEmployee() using POST.
    // Chain: .post().uri("/api/employees")
    //        .bodyValue(employee)
    //        .retrieve()
    //        .toBodilessEntity()     <- returns Mono<ResponseEntity<Void>>
    //        .block()
    // Extract the Location header: response.getHeaders().getLocation()
    // Return the Location URI as a String.
    public String createEmployee(Employee employee) {
        return employeeWebClient.post()
                .uri("/api/employees")
                .bodyValue(employee)
                .retrieve()
                .toBodilessEntity()
                .block()
                .getHeaders()
                .getLocation()
                .toString();
    }
}