package com.example.banking.model;

public record Account(
        String accountId,
        String ownerId,
        String accountType,
        double balance
) {}
