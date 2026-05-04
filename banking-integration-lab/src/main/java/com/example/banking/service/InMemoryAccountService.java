package com.example.banking.service;

import com.example.banking.domain.Account;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * In-memory implementation of AccountService.
 *
 * Used at runtime; replaced by a Mockito mock in tests via @MockBean.
 */
@Service
public class InMemoryAccountService implements AccountService {

    @Override
    public List<Account> findAll() {
        return List.of(
                new Account("ACC-001", "CHECKING", new BigDecimal("1500.00"), "ACTIVE"),
                new Account("ACC-002", "SAVINGS", new BigDecimal("8200.50"), "ACTIVE"));
    }
}
