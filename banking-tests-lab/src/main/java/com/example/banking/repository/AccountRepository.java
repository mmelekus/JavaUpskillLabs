package com.example.banking.repository;

import com.example.banking.Account;

import java.util.Optional;

/**
 * Repository interface for Account persistence.
 *
 * In Part 2, the TransferProcessor depends on this interface.
 * Tests will mock this interface with Mockito so that we can:
 * - Control what findById returns (including the case where no account exists)
 * - Verify that save was called with the correct updated state
 * - Test error paths without needing a real database
 */
public interface AccountRepository {

    Optional<Account> findById(String accountNumber);

    Account save(Account account);

    void delete(String accountNumber);
}
