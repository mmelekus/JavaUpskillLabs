package com.example.banking.model;

public record Transaction(
        String transactionId,
        String accountId,
        String type,
        double amount
) {
}
