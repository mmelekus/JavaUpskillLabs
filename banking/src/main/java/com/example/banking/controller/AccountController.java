package com.example.banking.controller;

import com.example.banking.dto.CreateAccountRequest;
import com.example.banking.model.Account;
import com.example.banking.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private static final List<Account> ACCOUNTS = List.of(
            new Account("ACC-001", "user-1", "CHECKING", 1500.00),
            new Account("ACC-002", "user-2", "SAVINGS", 8200.50),
            new Account("ACC-003", "user-1", "SAVINGS", 3100.75)
    );

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.findAll();
        // return ACCOUNTS;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable String accountId) {
        return accountService.findById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
//        return ACCOUNTS.stream()
//                .filter(acc -> acc.accountId().equals(accountId))
//                .findFirst()
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreateAccountRequest> createAccount(@Valid @RequestBody CreateAccountRequest account) {
        // Persistence is not implemented
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }
}


