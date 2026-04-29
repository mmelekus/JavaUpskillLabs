package com.example.bankserver.model;

public record Account(
        String accountNumber,
        AccountStatus status,
        double balance,
        AccountType type
) {
}