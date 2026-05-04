package com.example.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for TransferService.
 *
 * In Part 1 of the lab you will fill in this class with five tests.
 * The structure (imports, @BeforeEach, etc.) is given to you so you
 * can focus on the test logic, not the boilerplate.
 *
 * The C# parallel: this class plays the role NUnit's [TestFixture]
 * plays. The methods marked @Test are the test cases (NUnit's [Test]).
 * @BeforeEach is NUnit's [SetUp]. AssertJ's assertThat() is the same
 * pattern as FluentAssertions' Should().
 */
@DisplayName("Transfer service")
class TransferServiceTest {

    private TransferService transferService;

    @BeforeEach
    void setUp() {
        transferService = new TransferService();
    }

    // TODO 1.1 -- Test: transfer reduces the source account's balance
    //
    // Arrange: an active source with $100, an active destination with $0
    // Act:     transfer $30 from source to destination
    // Assert:  source balance is now $70
    @Test
    @DisplayName("transfer reduces the source account's balance")
    void transferReducesSourceBalance() {
        Account source = new Account("ACC-001", new BigDecimal("100.00"));
        Account destination = new Account("ACC-002", new BigDecimal("0.00"));

        transferService.transfer(source, destination, new BigDecimal("30.00"));

        assertThat(source.getBalance()).isEqualByComparingTo("70.00");
    }

    // TODO 1.2 -- Test: transfer increases the destination account's balance
    //
    // Arrange: an active source with $100, an active destination with $0
    // Act:     transfer $30 from source to destination
    // Assert:  destination balance is now $30
    @Test
    @DisplayName("transfer increases the destination account's balance")
    void transferIncreasesDestinationBalance() {
        Account source = new Account("ACC-001", new BigDecimal("100.00"));
        Account destination = new Account("ACC-002", new BigDecimal("0.00"));

        transferService.transfer(source, destination, new BigDecimal("30.00"));

        assertThat(destination.getBalance()).isEqualByComparingTo("30.00");
    }

    // TODO 1.3 -- Test: transfer rejects amounts greater than the source balance
    //
    // Arrange: an active source with $50, an active destination with $0
    // Act + Assert: transferring $100 should throw InsufficientFundsException
    // Use assertThatThrownBy(...).isInstanceOf(...).hasMessageContaining(...)
    @Test
    @DisplayName("transfer rejects amounts greater than the source balance")
    void transferRejectsAmountGreaterThenSourceBalance() {
        Account source = new Account("ACC-001", new BigDecimal("50.00"));
        Account destination = new Account("ACC-002", new BigDecimal("0.00"));

        assertThatThrownBy(() ->
                transferService.transfer(source, destination, new BigDecimal("100.00")))
            .isInstanceOf(InsufficientFundsException.class)
            .hasMessageContaining("ACC-001");
    }
    // TODO 1.4 -- Test: transfer rejects when the source account is frozen
    //
    // Arrange: a FROZEN source with $100, an active destination with $0
    // Act + Assert: any transfer should throw AccountFrozenException
    @Test
    @DisplayName("transfer rejects when the source account is frozen")
    void transferRejectsWhenSourceAccountIsFrozen() {
        Account source = new Account("ACC-001", new BigDecimal("100.00"), AccountStatus.FROZEN);
        Account destination = new Account("ACC-002", new BigDecimal("0.00"));

        assertThatThrownBy(
                () -> transferService.transfer(source, destination, new BigDecimal("30.00")))
            .isInstanceOf(AccountFrozenException.class)
            .hasMessageContaining("FROZEN");
    }
    // TODO 1.5 -- Parameterized test: calculateFee returns the right fee for each tier
    //
    // Use @ParameterizedTest with @CsvSource. Test these inputs and outputs:
    //   amount=50,    expectedFee=0.50
    //   amount=500,   expectedFee=1.50
    //   amount=2500,  expectedFee=5.00
    //   amount=10000, expectedFee=10.00
    @ParameterizedTest
    @CsvSource({
            "50, 0.50",
            "500, 1.50",
            "2500, 5.00",
            "10000, 10.00"
    })
    @DisplayName("calculateFee returns the right fee for each tier")
    void calculateFeeReturnsRightFeeForEachTier(BigDecimal amount, BigDecimal expectedFee) {
        BigDecimal fee = transferService.calculateFee(amount);

        assertThat(fee).isEqualByComparingTo(expectedFee);
    }
}
