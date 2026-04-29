package com.example.bankserver.model;

public record TransferResponse(
        String transactionId,
        TransactionStatus status
) {
}