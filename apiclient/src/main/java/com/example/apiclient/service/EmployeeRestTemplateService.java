package com.example.apiclient.service;

import com.example.apiclient.model.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeRestTemplateService {

    private final RestTemplate restTemplate;

    public EmployeeRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // TODO 2: Implement fetchAll() using getForObject.
    // The upstream URL is "/api/employees".
    // The return type must be Employee[].class (arrays are safe for getForObject).
    // Convert the array to a List before returning.
    // Hint: Arrays.asList(restTemplate.getForObject(...))
    public List<Employee> fetchAll() {
        Employee[] employees = restTemplate.getForObject("/api/employees", Employee[].class);
        return employees != null ? Arrays.asList(employees) : List.of(); // Replace with your implementation
    }

    // TODO 3: Implement fetchById() using getForEntity.
    // The upstream URL uses a URI variable: "/api/employees/{id}".
    // Return the body from the ResponseEntity.
    // Inspect the status code: if it is not 2xx, return null.
    // Hint: restTemplate.getForEntity("/api/employees/{id}", Employee.class, id)
    public Employee fetchById(Long id) {
        ResponseEntity<Employee> response = restTemplate.getForEntity("/api/employees/{id}", Employee.class, id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    // TODO 4: Implement fetchAllWithExchange() using exchange.
    // This method does the same job as fetchAll() but uses ParameterizedTypeReference
    // so the response is deserialized directly into List<Employee> rather than Employee[].
    // Hint:
    //   var typeRef = new ParameterizedTypeReference<List<Employee>>() {};
    //   ResponseEntity<List<Employee>> response = restTemplate.exchange(
    //       "/api/employees", HttpMethod.GET, HttpEntity.EMPTY, typeRef);
    //   return response.getBody();
    public List<Employee> fetchAllWithExchange() {
        var typeRef = new ParameterizedTypeReference<List<Employee>>() {};
        ResponseEntity<List<Employee>> response = restTemplate.exchange(
            "/api/employees", HttpMethod.GET, HttpEntity.EMPTY, typeRef);
        return response.getBody();
    }

    // TODO 5: Implement createEmployee() using postForEntity.
    // POST to "/api/employees" with the employee object as the request body.
    // Return the Location header from the response as a String.
    // Hint: restTemplate.postForEntity("/api/employees", employee, Employee.class)
    //       response.getHeaders().getLocation().toString()
    public String createEmployee(Employee employee) {
        ResponseEntity<Employee> response = restTemplate.postForEntity("/api/employees", employee, Employee.class);
        return response.getHeaders().getLocation().toString();
    }
}