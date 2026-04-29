package com.example.bankserver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TransferRequest(
        @NotBlank String fromAccountNumber,
        @NotBlank String toAccountNumber,
        @Positive double amount
) {
}