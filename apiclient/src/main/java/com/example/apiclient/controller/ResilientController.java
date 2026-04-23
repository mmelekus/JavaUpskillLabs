package com.example.apiclient.controller;

import com.example.apiclient.model.Employee;
import com.example.apiclient.service.ResilientEmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resilient")
public class ResilientController {

    private final ResilientEmployeeService service;

    public ResilientController(ResilientEmployeeService service) {
        this.service = service;
    }

    @GetMapping("/employees")
    public List<Employee> getAll() {
        return service.fetchAll();
    }
}