package com.example.apiclient.controller;

import com.example.apiclient.exception.EmployeeNotFoundException;
import com.example.apiclient.model.Employee;
import com.example.apiclient.service.EmployeeWebClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wc/employees")
public class EmployeeWebClientController {

    private final EmployeeWebClientService service;

    public EmployeeWebClientController(EmployeeWebClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.fetchAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return service.fetchById(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Employee employee) {
        var location = service.createEmployee(employee);
        return ResponseEntity.ok("Created at: " + location);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EmployeeNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}