/**
 * AccountList component.
 *
 * Displays the customer's accounts in a table. In Exercise 1 you will
 * implement this component to load its own accounts from the API. In
 * Exercise 3 you will refactor it to receive accounts as props from
 * the parent App component.
 *
 * For Exercise 1, complete the TODO sections below. The 'export function
 * AccountList()' takes no props yet because the component manages its
 * own state. This will change in Exercise 3.
 */

import { useState, useEffect } from 'react';
import { getAccounts } from '../api/client';
import type { Account } from '../api/types';
import { formatCurrency } from '../utils/format';

type AccountListProps = {
  accounts: Account[];
  loading: boolean;
  error: string | null;
};
export function AccountList({ accounts, loading, error }: AccountListProps) {
  // TODO 1.1: Declare three pieces of state:
  //   - accounts: an array of Account, initially empty
  //   - loading: a boolean, initially true
  //   - error: a string or null, initially null
  if (loading) {
    return <p className="status-message">Loading accounts...</p>;
  }

  if (error) {
    return <p className="status-message error">Error: {error}</p>;
  }

  // TODO 1.2: Add a useEffect that loads accounts from the API when
  // the component first renders. Update state with the result, or
  // set the error state if the call fails. Set loading to false
  // when the call completes (whether it succeeded or failed).

  // TODO 1.3: Add conditional rendering. If loading is true, return
  // a "Loading accounts..." message. If error is not null, return
  // an error message. Otherwise, fall through to the table below.

  return (
    <section className="account-list">
      <h2>Your Accounts</h2>
      <table>
        <thead>
          <tr>
            <th>Account Number</th>
            <th>Type</th>
            <th>Status</th>
            <th>Balance</th>
          </tr>
        </thead>
        <tbody>
          {/* TODO 1.4: Render one <tr> for each account in the accounts array.
              Each <tr> needs a unique 'key' prop set to account.accountNumber.
              Show the account number, type, status, and balance. Format
              the balance as currency, e.g. $1,234.56 */}
        </tbody>
      </table>
    </section>
  );
}
