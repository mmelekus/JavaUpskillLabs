package com.example.banking;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Simple banking account.
 *
 * Mutable on purpose: the TransferProcessor in Part 2 reads the account,
 * mutates the balance, and saves the updated account back via the repository.
 * In production code an immutable Account with a withBalance(...) method
 * would be more idiomatic; we keep it mutable here for simpler tests.
 */
public class Account {

    private final String accountNumber;
    private BigDecimal balance;
    private final AccountStatus status;

    public Account(String accountNumber, BigDecimal balance) {
        this(accountNumber, balance, AccountStatus.ACTIVE);
    }

    public Account(String accountNumber, BigDecimal balance, AccountStatus status) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account other)) return false;
        return Objects.equals(accountNumber, other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "Account{" + accountNumber + ", balance=" + balance + ", status=" + status + "}";
    }
}
