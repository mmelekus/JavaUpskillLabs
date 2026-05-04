package com.example.banking;

import com.example.banking.repository.AccountRepository;

import java.math.BigDecimal;

/**
 * Transfer processor that orchestrates a transfer using a repository.
 *
 * This class is the unit under test for Part 2 of the lab.
 * Unlike TransferService (Part 1), this class has a collaborator
 * (the AccountRepository), so testing it in isolation requires
 * mocking that collaborator with Mockito.
 *
 * The processor delegates the actual transfer logic to TransferService,
 * which keeps each class focused on one responsibility:
 * - TransferService: pure logic, no collaborators
 * - TransferProcessor: orchestration, fetches and persists state
 */
public class TransferProcessor {

    private final AccountRepository accountRepository;
    private final TransferService transferService;

    public TransferProcessor(AccountRepository accountRepository, TransferService transferService) {
        this.accountRepository = accountRepository;
        this.transferService = transferService;
    }

    /**
     * Process a transfer between two accounts identified by account number.
     *
     * Workflow:
     * 1. Fetch the source account from the repository
     * 2. Fetch the destination account from the repository
     * 3. Delegate the transfer logic to TransferService (validation + balance updates)
     * 4. Persist both accounts with their new balances
     *
     * If either account is missing, throws AccountNotFoundException.
     * Validation failures from TransferService propagate up.
     */
    public void processTransfer(String sourceAccountNumber,
                                String destinationAccountNumber,
                                BigDecimal amount) {

        Account source = accountRepository.findById(sourceAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException(
                        "source account not found: " + sourceAccountNumber));

        Account destination = accountRepository.findById(destinationAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException(
                        "destination account not found: " + destinationAccountNumber));

        transferService.transfer(source, destination, amount);

        accountRepository.save(source);
        accountRepository.save(destination);
    }
}
