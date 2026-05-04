package com.example.banking;

import java.math.BigDecimal;

/**
 * Pure-logic transfer service with no external collaborators.
 *
 * This class is the unit under test for Part 1 of the lab.
 * Every method is a pure function of its inputs; there is no
 * database, no clock, no external service. This means it can be
 * tested with JUnit and AssertJ alone, no mocking required.
 */
public class TransferService {

    private static final BigDecimal MAX_TRANSFER_AMOUNT = new BigDecimal("10000.00");

    /**
     * Transfer money between two accounts.
     *
     * The accounts are mutated in place: the source's balance is
     * decreased and the destination's balance is increased.
     *
     * Validation rules:
     * - amount must be positive (greater than zero)
     * - amount must not exceed MAX_TRANSFER_AMOUNT
     * - source account must have status ACTIVE
     * - destination account must have status ACTIVE
     * - source account balance must be at least equal to amount
     */
    public void transfer(Account source, Account destination, BigDecimal amount) {
        validateAmount(amount);
        validateAccountActive(source, "source");
        validateAccountActive(destination, "destination");
        validateSufficientFunds(source, amount);

        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));
    }

    /**
     * Calculate the fee for a transfer of the given amount.
     *
     * Fee structure:
     * - amount <= 100   : 0.50
     * - amount <= 1000  : 1.50
     * - amount <= 5000  : 5.00
     * - amount > 5000   : 0.1% of amount
     */
    public BigDecimal calculateFee(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }

        if (amount.compareTo(new BigDecimal("100.00")) <= 0) {
            return new BigDecimal("0.50");
        }
        if (amount.compareTo(new BigDecimal("1000.00")) <= 0) {
            return new BigDecimal("1.50");
        }
        if (amount.compareTo(new BigDecimal("5000.00")) <= 0) {
            return new BigDecimal("5.00");
        }
        return amount.multiply(new BigDecimal("0.001")).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (amount.compareTo(MAX_TRANSFER_AMOUNT) > 0) {
            throw new IllegalArgumentException(
                    "amount exceeds maximum transfer of " + MAX_TRANSFER_AMOUNT);
        }
    }

    private void validateAccountActive(Account account, String role) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountFrozenException(
                    role + " account " + account.getAccountNumber()
                            + " is " + account.getStatus());
        }
    }

    private void validateSufficientFunds(Account source, BigDecimal amount) {
        if (source.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    "account " + source.getAccountNumber()
                            + " has insufficient funds for transfer of " + amount);
        }
    }
}
