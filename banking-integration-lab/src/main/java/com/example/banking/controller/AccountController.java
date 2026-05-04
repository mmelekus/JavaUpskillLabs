package com.example.banking.controller;

import com.example.banking.domain.Account;
import com.example.banking.service.AccountService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller exposing account data.
 *
 * This is the unit under test for the integration tests in this lab.
 * Spring Security configuration (see SecurityConfig) requires
 * authentication on /api/** endpoints, so unauthenticated requests
 * return 401 and authenticated requests return the data.
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAccounts() {
        return accountService.findAll();
    }
}
