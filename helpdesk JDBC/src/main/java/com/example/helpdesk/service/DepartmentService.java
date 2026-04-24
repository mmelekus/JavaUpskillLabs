package com.example.helpdesk.service;

import com.example.helpdesk.dto.DepartmentDto;
import com.example.helpdesk.dto.TicketCreateRequest;
import com.example.helpdesk.dto.TicketDto;
import com.example.helpdesk.model.Department;
import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> findAll() {
        // TODO 5: Call departmentRepository.findAllOrderedByName(),
        // map each Department to a DepartmentDto using toDepartmentDto(),
        // and return the list.
        //
        // Note: Spring Data JDBC will execute one SELECT on DEPARTMENTS and then
        // one SELECT on TICKETS per department row returned. With 3 departments
        // this produces 4 queries total (1 + 3). Check the console log after
        // you implement and call this endpoint. Count the queries.
        //
        // This is not N+1 in the JPA sense. It is the defined aggregate loading
        // behaviour of Spring Data JDBC. You cannot avoid it for findAll().
        // If you need a different shape, write a custom @Query.
        List<Department> departments = departmentRepository.findAllOrderedByName();
        return departments.stream()
                .map(this::toDepartmentDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DepartmentDto findById(Long id) {
        // TODO 6: Use departmentRepository.findById(id).
        // Throw NoSuchElementException("Department not found: " + id) if absent.
        // Map and return the DepartmentDto.
        return departmentRepository.findById(id)
                .map(this::toDepartmentDto)
                .orElseThrow(() -> new NoSuchElementException("Department not found: " + id));
    }

    @Transactional
    public DepartmentDto addTicket(Long departmentId, TicketCreateRequest request) {
        // TODO 7: Load the department. Throw NoSuchElementException if not found.
        //
        // Construct a new Ticket from the request fields.
        // Set status to "OPEN" -- new tickets always start open.
        //
        // Call department.addTicket(ticket).
        //
        // Call departmentRepository.save(department).
        //
        // IMPORTANT: In Spring Data JDBC, save() on the aggregate root is always
        // required to persist a change. There is no dirty checking. If you modify
        // the department or add a ticket but do not call save(), nothing happens
        // to the database. This is the opposite of what you saw in Lab 3.3's
        // adjustStock() method, where Hibernate's dirty checking issued the UPDATE
        // without an explicit save() call.
        //
        // Return the updated DepartmentDto.
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found: " + departmentId));

        Ticket ticket = new Ticket(
                request.getTitle(),
                request.getDescription(),
                "OPEN",
                request.getPriority()
        );
        department.addTicket(ticket);

        Department saved = departmentRepository.save(department);
        return toDepartmentDto(saved);
    }

    @Transactional
    public DepartmentDto resolveTicket(Long departmentId, Long ticketId) {
        // TODO 8: Load the department. Throw NoSuchElementException if not found.
        //
        // Find the ticket in department.getTickets() whose ticketId matches.
        // Throw NoSuchElementException("Ticket not found: " + ticketId) if not found.
        //
        // Call ticket.setStatus("RESOLVED").
        //
        // Call departmentRepository.save(department).
        //
        // This is where the no-dirty-checking rule becomes concrete. You modified
        // the ticket's status field. In JPA, inside a @Transactional method,
        // that change would be detected automatically and an UPDATE would fire.
        // In Spring Data JDBC, calling setStatus() on the Ticket does nothing to
        // the database until you call save() on the owning Department aggregate root.
        // If you forget save(), you return a DTO with status = RESOLVED but Oracle
        // still has status = IN_PROGRESS. Run the endpoint without save() first
        // (Step 10.6), then add it back and compare the console output.
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found: " + departmentId));

        Ticket ticket = department.getTickets().stream()
                .filter(t -> t.getTicketId().equals(ticketId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Ticket not found: " + ticketId));

        ticket.setStatus("RESOLVED");

        Department saved = departmentRepository.save(department);
        return toDepartmentDto(saved);
    }

    @Transactional
    public DepartmentDto createDepartment(String name, String location) {
        // TODO 9: Check for a duplicate department name using findByName(name).
        // Throw IllegalArgumentException("Department already exists: " + name) if found.
        // Construct a new Department, call departmentRepository.save(), return the DTO.
        departmentRepository.findByName(name).ifPresent(existing -> {
            throw new IllegalArgumentException("Department already exists: " + name);
        });

        Department department = new Department(name, location);
        Department saved = departmentRepository.save(department);
        return toDepartmentDto(saved);
    }

    // Mapping helpers

    private DepartmentDto toDepartmentDto(Department department) {
        List<TicketDto> ticketDtos = department.getTickets().stream()
                .map(this::toTicketDto)
                .toList();
        return new DepartmentDto(
                department.getDepartmentId(),
                department.getName(),
                department.getLocation(),
                ticketDtos
        );
    }

    private TicketDto toTicketDto(Ticket ticket) {
        return new TicketDto(
                ticket.getTicketId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority()
        );
    }
}