package com.example.helpdesk.dto;

public class TicketDto {

    private Long ticketId;
    private String title;
    private String description;
    private String status;
    private String priority;

    public TicketDto(Long ticketId, String title, String description,
                     String status, String priority) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

    public Long getTicketId() { return ticketId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
}