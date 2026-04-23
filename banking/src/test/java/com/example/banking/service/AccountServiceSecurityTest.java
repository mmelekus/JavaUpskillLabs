package com.example.banking.service;

import com.example.banking.dto.CreateAccountRequest;
import com.example.banking.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AccountServiceSecurityTest {

    @Autowired
    private AccountService accountService;

    @Test
    @WithMockUser(authorities = {"SCOPE_read:accounts"})
    void findAll_withReadScope_returnsAccounts() {
        var result = accountService.findAll();
        assertThat(result).isNotEmpty();
    }

    @Test
    @WithMockUser
    void findAll_withoutReadScope_throwsAccessDeniedException() {
        assertThatThrownBy(() -> accountService.findAll())
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void findAll_withNoAuthentication_throwsAccessDeniedException() {
        assertThatThrownBy(() -> accountService.findAll())
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_write:accounts", "SCOPE_read:accounts"})
    void create_withWriteAndReadScope_succeeds() {
        CreateAccountRequest request = new CreateAccountRequest(
                "user-1", "SAVINGS", 500.00, "Test account");

        Account result = accountService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.accountId()).isNotBlank();
        assertThat(result.ownerId()).isEqualTo("user-1");
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_write:accounts"})
    void create_withWriteScopeButNoReadScope_throwsAccessDeniedException() {
        CreateAccountRequest request = new CreateAccountRequest(
                "user-1", "SAVINGS", 500.00, "Test account");

        assertThatThrownBy(() -> accountService.create(request))
                .isInstanceOf(AccessDeniedException.class);
    }
}