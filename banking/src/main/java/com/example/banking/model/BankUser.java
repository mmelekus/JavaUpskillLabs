package com.example.banking.model;

public record BankUser(
        String userId,
        String userName,
        String email,
        String role
) {
}
