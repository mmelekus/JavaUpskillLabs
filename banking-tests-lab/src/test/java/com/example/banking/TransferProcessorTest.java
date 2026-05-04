package com.example.banking;

import com.example.banking.repository.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for TransferProcessor.
 *
 * In Part 2 of the lab you will fill in this class with four tests
 * that use Mockito to mock the AccountRepository collaborator.
 *
 * The C# parallel: @Mock here plays the role of Mock<T> in Moq.
 * when().thenReturn() is Setup().Returns(). verify() is Verify().
 * The pattern is the same; the syntax is what differs.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Transfer processor")
class TransferProcessorTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferProcessor transferProcessor;

    // TODO 2.1 -- Test: processTransfer fetches both accounts from the repository
    //
    // Arrange: stub findById("ACC-001") to return an active account with $100
    //          stub findById("ACC-002") to return an active account with $0
    // Act:     call transferProcessor.processTransfer("ACC-001", "ACC-002", new BigDecimal("30.00"))
    // Assert:  verify findById was called once with "ACC-001"
    //          verify findById was called once with "ACC-002"

    // TODO 2.2 -- Test: processTransfer saves both accounts after a successful transfer
    //
    // Arrange: stub findById for both accounts
    // Act:     call processTransfer with valid arguments
    // Assert:  verify save was called exactly twice (once per account)
    //          Use verify(accountRepository, times(2)).save(any(Account.class))

    // TODO 2.3 -- Test: processTransfer throws AccountNotFoundException when source is missing
    //
    // Arrange: stub findById("ACC-MISSING") to return Optional.empty()
    // Act + Assert: calling processTransfer should throw AccountNotFoundException
    //               with a message containing "ACC-MISSING"
    // Also assert: verify save was NEVER called (the failure happened before save)

    // TODO 2.4 -- Test: processTransfer captures the saved source account with the updated balance
    //
    // Arrange: stub findById to return real Account objects (so the real
    //          TransferService can mutate them; do NOT mock TransferService here,
    //          replace the @Mock TransferService with a real TransferService)
    // Act:     processTransfer 30.00 from ACC-001 (balance 100) to ACC-002 (balance 0)
    // Assert:  use ArgumentCaptor to capture every Account passed to save(),
    //          then assert one of them is ACC-001 with balance 70.00
    //
    // Hint: ArgumentCaptor.getAllValues() returns a List<Account> when
    //       save() was called multiple times.
}
