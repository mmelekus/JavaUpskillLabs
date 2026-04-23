package com.example.apiclient.controller;

import com.example.apiclient.service.AuthenticatedEmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticatedEmployeeService service;

    public AuthController(AuthenticatedEmployeeService service) {
        this.service = service;
    }

    @GetMapping("/ping")
    public String ping() {
        return service.ping();
    }
}