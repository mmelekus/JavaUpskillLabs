package com.example.helpdesk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

// No @Entity annotation. No Hibernate. This is a plain Java class.
// Spring Data JDBC uses its own mapping layer, configured in JdbcConfig.
@Table("tickets")
public class Ticket {

    // TODO 1: Add @Id to this field.
    // In Spring Data JDBC, @Id is from org.springframework.data.annotation.Id,
    // NOT from jakarta.persistence.Id. Make sure your import statement is correct.
    // Using the wrong @Id annotation is the single most common mistake in this lab.
    @Id
    @Column("ticket_id")
    private Long ticketId;

    // Note: there is no @ManyToOne here and no 'department' field.
    // Spring Data JDBC does not support navigable object references across aggregate boundaries.
    // The department_id foreign key is managed by the framework automatically.
    // You reference departments by ID, not by object.

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("status")
    private String status;

    @Column("priority")
    private String priority;

    // @ReadOnlyProperty tells Spring Data JDBC to read this column on SELECT
    // but never include it in INSERT or UPDATE statements.
    // Oracle's DEFAULT SYSDATE then populates the value automatically on insert.
    // Import: org.springframework.data.annotation.ReadOnlyProperty
    // NOT jakarta.persistence.Column (which has insertable = false).
    @ReadOnlyProperty
    @Column("created_date")
    private LocalDate createdDate;

    // Spring Data JDBC requires a default constructor
    public Ticket() {}

    public Ticket(String title, String description, String status, String priority) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDate getCreatedDate() { return createdDate; }
}