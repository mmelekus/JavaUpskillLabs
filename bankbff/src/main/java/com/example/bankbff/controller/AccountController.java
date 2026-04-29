package com.example.bankbff.controller;

import com.example.bankbff.client.BankingApiClient;
import com.example.bankbff.dto.AccountDto;
import com.example.bankbff.dto.TransferRequestDto;
import com.example.bankbff.dto.TransferResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Banking endpoints exposed to the SPA.
 *
 * Each method delegates to BankingApiClient, which calls the downstream
 * resource server. The bearer token is attached automatically by the
 * WebClient's OAuth2 filter.
 *
 * Spring Security gates these endpoints based on the rules in SecurityConfig.
 * Unauthenticated requests are rejected with 401 before they reach this
 * controller.
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    private final BankingApiClient bankingApiClient;

    public AccountController(BankingApiClient bankingApiClient) {
        this.bankingApiClient = bankingApiClient;
    }

    @GetMapping("/accounts")
    public List<AccountDto> accounts() {
        // TODO 7.2: Delegate to bankingApiClient.getAccounts() and return
        // the result.

        return bankingApiClient.getAccounts();
    }

    @PostMapping("/transfers")
    public TransferResponseDto transfer(@RequestBody TransferRequestDto request) {
        // TODO 7.3: Delegate to bankingApiClient.postTransfer(request) and
        // return the result.

        return bankingApiClient.postTransfer(request);
    }
}