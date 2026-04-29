/**
 * TransferForm component.
 *
 * Lets the customer transfer funds between accounts. Receives the list
 * of accounts and a callback prop from the parent App component. After
 * a successful transfer the form invokes onTransferComplete so the
 * parent can refresh the displayed balances.
 *
 * For Exercise 2, complete the TODO sections below.
 */

import { useState } from 'react';
import { postTransfer } from '../api/client';
import type { Account } from '../api/types';

type TransferFormProps = {
  accounts: Account[];
  onTransferComplete: () => void;
};

export function TransferForm({ accounts, onTransferComplete }: TransferFormProps) {
  // TODO 2.1: Declare state for the three form fields:
  //   - fromAccount: a string, initially ''
  //   - toAccount: a string, initially ''
  //   - amount: a string, initially ''
  // (Why string for amount? Form inputs always produce strings;
  // we will convert to a number when we submit.)


  // TODO 2.2: Declare state for the API call status:
  //   - submitting: a boolean, initially false
  //   - message: a string or null, initially null
  //   - messageType: 'success' or 'error' or null, initially null


  // TODO 2.3: Write the submit handler. It should:
  //   - Prevent the browser's default form submission
  //   - Set submitting to true and clear any previous message
  //   - Call postTransfer with the current form values
  //     (convert amount with parseFloat)
  //   - On success: set a success message, clear the form, and
  //     call onTransferComplete to refresh the account list
  //   - On failure: set an error message
  //   - In all cases: set submitting back to false


  return (
    <section className="transfer-form">
      <h2>Transfer Money</h2>
      {/* TODO 2.4: Wire the onSubmit prop on the form below to the handler */}
      <form>
        <div className="form-row">
          <label htmlFor="fromAccount">From Account</label>
          {/* TODO 2.5: Make this a controlled select.
              - The 'value' prop should be fromAccount.
              - The 'onChange' handler should update fromAccount.
              - The options should be one per account in the accounts prop. */}
          <select id="fromAccount">
            <option value="">-- Select an account --</option>
          </select>
        </div>

        <div className="form-row">
          <label htmlFor="toAccount">To Account Number</label>
          {/* TODO 2.6: Make this a controlled text input.
              - The 'value' prop should be toAccount.
              - The 'onChange' handler should update toAccount. */}
          <input id="toAccount" type="text" placeholder="Destination account number" />
        </div>

        <div className="form-row">
          <label htmlFor="amount">Amount</label>
          {/* TODO 2.7: Make this a controlled text input.
              - The 'value' prop should be amount.
              - The 'onChange' handler should update amount.
              - type="number" hints at numeric input but the value is still a string. */}
          <input id="amount" type="number" step="0.01" min="0.01" placeholder="0.00" />
        </div>

        {/* TODO 2.8: When submitting is true, the button should be disabled and show "Submitting..."
                     Otherwise the button should be enabled and show "Transfer". */}
        <button type="submit">Transfer</button>

        {/* TODO 2.9: If message is not null, render a <p> with the message.
                     Use the className "form-message success" or "form-message error"
                     based on messageType. */}
      </form>
    </section>
  );
}
