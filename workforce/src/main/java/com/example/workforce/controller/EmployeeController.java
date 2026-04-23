package com.example.workforce.controller;

import com.example.workforce.model.Employee;
import com.example.workforce.service.AuditService;
import com.example.workforce.service.DownstreamEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final AuditService auditService;

    private static final List<Employee> EMPLOYEES = List.of(
            new Employee("E001", "Alice Chen", "Engineering", "ENGINEER"),
            new Employee("E002", "Bob Okafor", "Engineering", "SENIOR_ENGINEER"),
            new Employee("E003", "Carol Diaz", "Human Resources", "HR_MANAGER")
    );
    private final DownstreamEmployeeService downstreamEmployeeService;

    public EmployeeController(AuditService auditService, DownstreamEmployeeService downstreamEmployeeService) {
        this.auditService = auditService;
        this.downstreamEmployeeService = downstreamEmployeeService;
    }

    @GetMapping
    public List<Employee> getAll() {
        return EMPLOYEES;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable String id) {
        auditService.logEvent("READ_EMPLOYEE", id);

        return EMPLOYEES.stream()
                .filter(e -> e.id().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // TODO 1: Add a POST endpoint that accepts an Employee in the request body
    // and returns 201 Created with the employee in the response body.
    // The endpoint does not need to persist the employee -- this is a stub.
    // Annotate the parameter with @Requestbody.
    // Use ResponseEntitye.status(HttpStatus.CREATED).body(Employee) as the return value.
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    // TODO: 6: Complete this endpoint.
    // @AuthenticationPrincipal instructs Spring Security to inject the validated Jwt
    // from the SecurityContext directly as a method parameter.
    // This is cleaner than calling SecurityContextHolder.geetContext() manually.
    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        // TODO 7: Return a Map containing:
        //    "subject"       -- jwt.getSubject()
        //    "issuer"        -- jwt.getIssuer().toString()
        //    "scopes"        -- jwt.getClaimAsString("scope")
        //    "tokenExpiry"   -- jwt.getExpiresAt().toString()
        //    "roles"         -- jwt.getClaimAsStringList("roles"), or an empty list if null
        //    "department"    -- jwt.getClaimAsString("department"), or "not present" if null
        //
        // The service token will not have "roles" or "department" because the
        // token customizer in the Authorization Server only adds those for
        // user-context tokens. This difference is the main thing to observe here.
        List<String> roles = jwt.getClaimAsStringList("roles");
        String  department = jwt.getClaimAsString("department");

        Map<String, Object> result = new HashMap<>();
        result.put("subject", jwt.getSubject());
        result.put("issuer", jwt.getIssuer().toString());
        result.put("scopes", jwt.getClaimAsString("scope"));
        result.put("tokenExpiry", jwt.getExpiresAt().toString());
        result.put("roles", roles != null ? roles : List.of());
        result.put("department", department != null ? department : "not present");

        return result; // Replace with your implementation
    }

    @GetMapping("/downstream")
    public List<Employee> getFromDownstream() {
        return downstreamEmployeeService.fetchAllFromDownstream();
    }
}
