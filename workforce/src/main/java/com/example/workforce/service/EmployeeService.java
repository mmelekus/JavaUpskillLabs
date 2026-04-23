package com.example.workforce.service;

import com.example.workforce.model.Employee;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmployeeService {

    private final Map<String, Employee> store = new ConcurrentHashMap<>();

    public EmployeeService() {
        store.put("E001", new Employee("E001", "Alice Chen", "Engineering", "ENGINEER"));
        store.put("E002", new Employee("E002", "Bob Okafor", "Engineering", "SENIOR_ENGINEER"));
        store.put("E003", new Employee("E003", "Carol Diaz", "Human Resources", "HR_MANAGER"));
    }

    // TODO 10: Add @PreAuthorize("hasAuthority('SCOPE_read:employees')")
    // Any caller -- HTTP, Kafka, scheduled job -- must have this scope.
    @PreAuthorize("hasAuthority('SCOPE_read:employees')")
    public List<Employee> findAll() {
        return new ArrayList<>(store.values());
    }

    // TODO 11: Add @PreAuthorize("hasAuthority('SCOPE_read:employees')")
    @PreAuthorize("hasAuthority('SCOPE_read:employees')")
    public Optional<Employee> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    // TODO 12: Add @PreAuthorize that requires BOTH:
    //   hasAuthority('SCOPE_write:employees') AND hasRole('HR_MANAGER')
    // Combine them with &&.
    // A service account with write scope but no HR_MANAGER role will be denied.
    @PreAuthorize("hasAuthority('SCOPE_write:employees') && hasRole('HR_MANAGER')")
    public Employee save(Employee employee) {
        store.put(employee.id(), employee);
        return employee;
    }

    // TODO 13: Add @PreAuthorize("hasRole('HR_MANAGER')")
    // Also add @PostAuthorize to check the return value after the method runs:
    //   "returnObject.isPresent() && (returnObject.get().department() == 'Human Resources'
    //    || hasRole('ADMIN'))"
    @PreAuthorize("hasRole('HR_MANAGER')")
    @PostAuthorize("returnObject.isPresent() && (returnObject.get().department() =='Human Resources' || hasRole('ADMIN'))")
    public Optional<Employee> findByIdForHr(String id) {
        return Optional.ofNullable(store.get(id));
    }

    // TODO 14: After completing the other TODOs, add @PreAuthorize here
    // that references the method argument using the # prefix:
    //   "hasRole('ADMIN') || #department == authentication.name"
    @PreAuthorize("hasRole('ADMIN') || #department == authentication.name")
    public List<Employee> findByDepartment(String department) {
        return store.values().stream()
                .filter(e -> e.department().equals(department))
                .toList();
    }

    // Calls findAll() via 'this' -- which bypasses the AOP proxy.
    // The @PreAuthorize on findAll() is NOT enforced for this call.
    // This is the most common AOP proxy gotcha in Spring applications.
    public List<Employee> findAllInternal() {
        return this.findAll();
    }
}