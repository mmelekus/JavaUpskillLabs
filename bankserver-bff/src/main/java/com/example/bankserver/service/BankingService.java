package com.example.bankserver.service;

import com.example.bankserver.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * In-memory banking service.
 *
 * Holds account state and processes transfers. The same accounts are returned
 * that Lab 4.5's MSW mock returned, so the data feels continuous when the SPA
 * is wired up in Lab 4.7.
 */
@Service
public class BankingService {

    private final List<Account> accounts = new ArrayList<>(List.of(
            new Account("ACC-001", AccountStatus.ACTIVE,   1500.00, AccountType.CHECKING),
            new Account("ACC-002", AccountStatus.ACTIVE,   8200.50, AccountType.SAVINGS),
            new Account("ACC-003", AccountStatus.ACTIVE,   3100.75, AccountType.SAVINGS),
            new Account("ACC-004", AccountStatus.INACTIVE, 0.00,    AccountType.CHECKING)
    ));

    private final ReentrantLock lock = new ReentrantLock();

    public List<Account> listAccounts() {
        lock.lock();
        try {
            return List.copyOf(accounts);
        } finally {
            lock.unlock();
        }
    }

    public TransferResponse transfer(TransferRequest request) {
        lock.lock();
        try {
            int fromIndex = findIndexByAccountNumber(request.fromAccountNumber());
            if (fromIndex == -1) {
                return new TransferResponse(null, TransactionStatus.FAILED);
            }
            Account fromAccount = accounts.get(fromIndex);

            if (fromAccount.status() != AccountStatus.ACTIVE) {
                return new TransferResponse(null, TransactionStatus.FAILED);
            }

            if (request.amount() <= 0 || request.amount() > fromAccount.balance()) {
                return new TransferResponse(null, TransactionStatus.FAILED);
            }

            Account debited = new Account(
                    fromAccount.accountNumber(),
                    fromAccount.status(),
                    fromAccount.balance() - request.amount(),
                    fromAccount.type()
            );
            accounts.set(fromIndex, debited);

            int toIndex = findIndexByAccountNumber(request.toAccountNumber());
            if (toIndex != -1) {
                Account toAccount = accounts.get(toIndex);
                Account credited = new Account(
                        toAccount.accountNumber(),
                        toAccount.status(),
                        toAccount.balance() + request.amount(),
                        toAccount.type()
                );
                accounts.set(toIndex, credited);
            }

            String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            return new TransferResponse(transactionId, TransactionStatus.COMPLETE);
        } finally {
            lock.unlock();
        }
    }

    private int findIndexByAccountNumber(String accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).accountNumber().equals(accountNumber)) {
                return i;
            }
        }
        return -1;
    }
}