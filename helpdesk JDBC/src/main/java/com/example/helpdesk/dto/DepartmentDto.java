package com.example.helpdesk.dto;

import java.util.List;

public class DepartmentDto {

    private Long departmentId;
    private String name;
    private String location;
    private List<TicketDto> tickets;

    public DepartmentDto(Long departmentId, String name,
                         String location, List<TicketDto> tickets) {
        this.departmentId = departmentId;
        this.name = name;
        this.location = location;
        this.tickets = tickets;
    }

    public Long getDepartmentId() { return departmentId; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<TicketDto> getTickets() { return tickets; }
}