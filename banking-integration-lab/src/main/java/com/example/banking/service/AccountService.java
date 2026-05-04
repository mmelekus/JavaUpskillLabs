package com.example.banking.service;

import com.example.banking.domain.Account;

import java.util.List;

/**
 * Service interface for account operations.
 *
 * The controller depends on this interface. In the integration test,
 * we use @MockBean to provide a mock implementation so the test does
 * not depend on any real data store.
 */
public interface AccountService {

    List<Account> findAll();
}
