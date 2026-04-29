package com.example.bankbff.dto;

/**
 * Account DTO returned by the BFF to the SPA.
 *
 * Mirrors the shape of the Account record returned by the resource server.
 * In a more sophisticated BFF you might transform the shape here, but for
 * Lab 4.6 we just pass the data through.
 */
public record AccountDto(
        String accountNumber,
        String status,
        double balance,
        String type
) {
}