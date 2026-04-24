package com.example.catalog.entity;

import jakarta.persistence.*;

// @NamedStoredProcedureQuery registers the Oracle stored procedure with JPA.
// name:          the logical name used in Java code to look up this declaration
// procedureName: the exact name of the procedure in Oracle
// parameters:    declares each parameter with its name, mode (IN/OUT), and Java type
//
// The parameter names here ("p_department_id", "p_open_count") must match
// the parameter names declared in the PL/SQL procedure exactly.
@NamedStoredProcedureQuery(
        name = "Department.getOpenTicketCount",
        procedureName = "get_open_ticket_count",
        parameters = {
                @StoredProcedureParameter(
                        name = "p_department_id",
                        mode = ParameterMode.IN,
                        type = Long.class
                ),
                @StoredProcedureParameter(
                        name = "p_open_count",
                        mode = ParameterMode.OUT,
                        type = Long.class
                )
        }
)
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", length = 100)
    private String location;

    public Long getDepartmentId() { return departmentId; }
    public String getName() { return name; }
    public String getLocation() { return location; }
}