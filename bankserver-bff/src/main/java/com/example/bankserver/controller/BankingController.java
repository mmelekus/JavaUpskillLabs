package com.example.bankserver.controller;

import com.example.bankserver.model.Account;
import com.example.bankserver.model.TransferRequest;
import com.example.bankserver.model.TransferResponse;
import com.example.bankserver.service.BankingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Banking REST API controller.
 *
 * All endpoints require a valid JWT bearer token, enforced by SecurityConfig.
 */
@RestController
public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @GetMapping("/accounts")
    public List<Account> listAccounts() {
        return bankingService.listAccounts();
    }

    @PostMapping("/transfers")
    public TransferResponse transfer(@Valid @RequestBody TransferRequest request) {
        return bankingService.transfer(request);
    }
}