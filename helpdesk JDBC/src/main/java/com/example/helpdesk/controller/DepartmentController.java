package com.example.helpdesk.controller;

import com.example.helpdesk.dto.DepartmentDto;
import com.example.helpdesk.dto.TicketCreateRequest;
import com.example.helpdesk.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<DepartmentDto> getAll() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(departmentService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> create(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            String location = body.get("location");
            DepartmentDto created = departmentService.createDepartment(name, location);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // TODO 10: Implement POST /api/v1/departments/{id}/tickets.
    // Accept a @Valid @RequestBody TicketCreateRequest.
    // Call departmentService.addTicket(id, request).
    // Return 201 CREATED with the updated DepartmentDto as the body.
    // Return 404 if the department is not found.
    @PostMapping("/{id}/tickets")
    public ResponseEntity<DepartmentDto> addTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketCreateRequest request) {
        try {
            DepartmentDto updated = departmentService.addTicket(id, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO 11: Implement PATCH /api/v1/departments/{departmentId}/tickets/{ticketId}/resolve.
    // No request body is needed. Call departmentService.resolveTicket(departmentId, ticketId).
    // Return 200 OK with the updated DepartmentDto.
    // Return 404 if either the department or the ticket is not found.
    @PatchMapping("/{departmentId}/tickets/{ticketId}/resolve")
    public ResponseEntity<DepartmentDto> resolveTicket(
            @PathVariable Long departmentId,
            @PathVariable Long ticketId) {
        try {
            return ResponseEntity.ok(departmentService.resolveTicket(departmentId, ticketId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}