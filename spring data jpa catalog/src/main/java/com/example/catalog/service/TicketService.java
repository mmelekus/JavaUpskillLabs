package com.example.catalog.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    private final EntityManager entityManager;

    // EntityManager is the JPA session through which all database operations run.
    // Spring injects it automatically when declared as a constructor parameter.
    public TicketService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public long getOpenTicketCount(Long departmentId) {

        // Look up the named stored procedure declaration registered on Department.
        // "Department.getOpenTicketCount" is the logical name from the
        // @NamedStoredProcedureQuery annotation on the Department entity.
        StoredProcedureQuery query = entityManager
                .createNamedStoredProcedureQuery("Department.getOpenTicketCount");

        // Bind the IN parameter by the name declared in the annotation.
        query.setParameter("p_department_id", departmentId);

        // execute() sends the call to Oracle.
        // Internally JPA uses a JDBC CallableStatement: {call get_open_ticket_count(?, ?)}
        // Oracle runs the procedure and populates the OUT parameter.
        query.execute();

        // Read the OUT parameter by name.
        // The value is returned as the declared type (Long.class).
        Long count = (Long) query.getOutputParameterValue("p_open_count");
        return count != null ? count : 0L;
    }
}