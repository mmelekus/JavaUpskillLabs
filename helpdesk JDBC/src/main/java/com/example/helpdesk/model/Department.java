package com.example.helpdesk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table("departments")
public class Department {

    @Id
    @Column("department_id")
    private Long departmentId;

    @Column("name")
    private String name;

    @Column("location")
    private String location;

    // @ReadOnlyProperty tells Spring Data JDBC to read this column on SELECT
    // but never include it in INSERT or UPDATE statements.
    // Oracle's DEFAULT SYSDATE populates the value automatically on insert.
    @ReadOnlyProperty
    @Column("created_date")
    private LocalDate createdDate;

    // TODO 2: Add the @MappedCollection annotation to the tickets field.
    //
    // @MappedCollection tells Spring Data JDBC that this Set<Ticket> maps to rows
    // in the TICKETS table. The idColumn attribute names the foreign key column
    // in the TICKETS table that references this aggregate root.
    //
    // Use: @MappedCollection(idColumn = "department_id")
    //
    // Compare this to JPA's @OneToMany(mappedBy = "department", fetch = FetchType.LAZY).
    // The critical differences:
    //   - There is no mappedBy because Ticket has no 'department' field to map by.
    //   - There is no FetchType parameter because there is no lazy loading.
    //     When you load a Department, its tickets are ALWAYS loaded. Always.
    //   - Spring Data JDBC uses a Set rather than a List by convention.
    @MappedCollection(idColumn = "department_id")
    private Set<Ticket> tickets = new HashSet<>();

    public Department() {}

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Convenience method to add a ticket to this aggregate.
    // Because Ticket has no reference back to its department,
    // you add tickets by calling this method and then saving the Department.
    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getCreatedDate() { return createdDate; }

    public Set<Ticket> getTickets() { return tickets; }
    public void setTickets(Set<Ticket> tickets) { this.tickets = tickets; }
}