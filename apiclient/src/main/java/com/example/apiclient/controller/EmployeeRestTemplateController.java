package com.example.apiclient.controller;

import com.example.apiclient.model.Employee;
import com.example.apiclient.service.EmployeeRestTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rt/employees")
public class EmployeeRestTemplateController {

    private final EmployeeRestTemplateService service;

    public EmployeeRestTemplateController(EmployeeRestTemplateService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.fetchAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        var employee = service.fetchById(id);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @GetMapping("/exchange")
    public List<Employee> getAllWithExchange() {
        return service.fetchAllWithExchange();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Employee employee) {
        var location = service.createEmployee(employee);
        return ResponseEntity.ok("Created at: " + location);
    }
}