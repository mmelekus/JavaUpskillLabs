package com.example.catalog.controller;

import com.example.catalog.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/departments")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}/open-ticket-count")
    public ResponseEntity<Map<String, Object>> getOpenTicketCount(
            @PathVariable Long id) {

        long count = ticketService.getOpenTicketCount(id);

        // Return a simple JSON object with the department ID and the count.
        // A dedicated DTO class would be appropriate in a larger application.
        return ResponseEntity.ok(Map.of(
                "departmentId", id,
                "openTicketCount", count
        ));
    }
}