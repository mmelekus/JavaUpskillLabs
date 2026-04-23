package com.example.workforce.service;

import com.example.workforce.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class EmployeeServiceSecurityTest {

    @Autowired
    private EmployeeService employeeService;

    // @WithMockUser simulates an authenticated user in the SecurityContext.
    // "SCOPE_read:employees" is the authority Spring Security derives from the
    // "read:employees" scope in a JWT -- the SCOPE_ prefix is added automatically.
    @Test
    @WithMockUser(authorities = {"SCOPE_read:employees"})
    void findAll_withReadScope_returnsEmployees() {
        var result = employeeService.findAll();
        assertThat(result).isNotEmpty();
    }

    @Test
    @WithMockUser
    void findAll_withoutReadScope_throwsAccessDeniedException() {
        // TODO 15: Assert that calling employeeService.findAll() throws AccessDeniedException.
        assertThatThrownBy(() -> employeeService.findAll())
                .isInstanceOf(AccessDeniedException.class);
    }

    // TODO 16: Write a test verifying save() succeeds with both
    // SCOPE_write:employees AND ROLE_HR_MANAGER.
    // @WithMockUser(authorities = {"SCOPE_write:employees"}, roles = {"HR_MANAGER"})
    // The roles attribute automatically adds "ROLE_HR_MANAGER" as an authority.
    @Test
    @WithMockUser(authorities = {"SCOPE_write:employees", "ROLE_HR_MANAGER"})
    void save_withWriteScopeAndHrManagerRole_succeeds() {
        // TODO: Create an Employee and call save(). Assert the return value is not null.
        Employee employee = new Employee("E004", "Dana Park", "Human Resources", "HR_COORDINATOR");
        Employee saved = employeeService.save(employee);
        assertThat(saved).isNotNull();
        assertThat(saved.id()).isEqualTo("E004");
    }

    // TODO 17: Write a test verifying save() throws AccessDeniedException when the
    // user has write scope but NOT the HR_MANAGER role.
    @Test
    @WithMockUser(authorities = {"SCOPE_write:employees"})
    void save_withWriteScopeButNoHrManagerRole_throwsAccessDeniedException() {
        // TODO: Assert that save() throws AccessDeniedException.
        Employee employee = new Employee("E005", "Eve Russo", "Engineering", "ENGINEER");
        assertThatThrownBy(() -> employeeService.save(employee))
                .isInstanceOf(AccessDeniedException.class);
    }


    @Test
    void findAllInternal_bypassesMethodSecurity() {
        var result =  employeeService.findAllInternal();
        assertThat(result).isNotEmpty();
    }
}