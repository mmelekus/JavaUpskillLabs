package com.example.banking.domain;

import java.math.BigDecimal;

/**
 * Account record returned by the controller as JSON.
 * Using a record for immutability and minimal boilerplate.
 */
public record Account(
        String accountNumber,
        String accountType,
        BigDecimal balance,
        String status) {
}
