package com.example.banking.controller;

import com.example.banking.config.SecurityConfig;
import com.example.banking.domain.Account;
import com.example.banking.service.AccountService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for AccountController.
 *
 * The @WebMvcTest annotation loads only the MVC infrastructure
 * (controllers, JSON converters, security filters), not the full
 * application context. This makes the test fast while still
 * exercising real Spring framework behavior.
 *
 * @Import(SecurityConfig.class) is required because @WebMvcTest does
 * not auto-load custom SecurityFilterChain beans. Without this import,
 * the test would use Spring Security's default configuration (which
 * includes an OAuth redirect entry point) and unauthenticated requests
 * would return 302 instead of 401.
 *
 * @MockBean provides a mock implementation of AccountService that
 * Spring injects into the controller. The real InMemoryAccountService
 * is NOT loaded.
 *
 * Tests use Spring Security's oauth2Login() post-processor to
 * simulate an authenticated user without performing a real OAuth flow.
 */
@WebMvcTest(AccountController.class)
@Import(SecurityConfig.class)
@DisplayName("Account controller")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    // TODO 1.1 -- Test: unauthenticated request returns 401
    //
    // 1. Use mockMvc.perform(get("/api/accounts")) WITHOUT any .with(oauth2Login())
    // 2. Assert .andExpect(status().isUnauthorized())
    // 3. No need to stub accountService -- the request never reaches the controller
    @Test
    @DisplayName("Unauthenticated request returns 401")
    void unauthenticatedRequestReturns401() throws Exception {
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isUnauthorized());
    }

    // TODO 1.2 -- Test: authenticated request returns 200 with the account list
    //
    // 1. Stub accountService.findAll() to return a list with one Account:
    //      new Account("ACC-001", "CHECKING", new BigDecimal("1500.00"), "ACTIVE")
    // 2. Perform GET /api/accounts WITH .with(oauth2Login())
    // 3. Assert status().isOk()
    // 4. Assert jsonPath("$[0].accountNumber").value("ACC-001")
    // 5. Assert jsonPath("$[0].balance").value(1500.00)
    @Test
    @DisplayName("Authenticated request returns 200 with the account list")
    void authenticatedRequestReturnsAccountList() throws Exception {
        when(accountService.findAll()).thenReturn(List.of(
                new Account("ACC-001", "CHECKING", new BigDecimal("1500.00"), "ACTIVE")
        ));

        mockMvc.perform(get("/api/accounts")
                        .with(oauth2Login()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value("ACC-001"))
                .andExpect(jsonPath("$[0].balance").value(1500.00));
    }
    // TODO 1.3 -- Test: authenticated request returns an empty list when service has no accounts
    //
    // 1. Stub accountService.findAll() to return List.of()
    // 2. Perform GET /api/accounts WITH .with(oauth2Login())
    // 3. Assert status().isOk()
    // 4. Assert jsonPath("$").isArray()
    // 5. Assert jsonPath("$.length()").value(0)
    @Test
    @DisplayName("Authenticated request returns empty list when service has no accounts")
    void authenticatedRequestReturnsEmptyListWhenServiceHasNoAccounts() throws Exception {
        when(accountService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/accounts")
                        .with(oauth2Login()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
