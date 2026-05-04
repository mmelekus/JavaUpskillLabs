/**
 * Root component.
 *
 * Reads the auth state and decides what to render:
 *  - loading: show a loading message
 *  - not logged in: show the SignInScreen
 *  - logged in: show the accounts list and transfer form
 *
 * App.tsx now owns the accounts data. It loads accounts after the
 * user is authenticated and passes the data down to AccountList
 * and TransferForm as props. After a successful transfer, the
 * onTransferComplete callback re-fetches accounts so balances update.
 */

import { useState, useEffect, useCallback } from 'react';
import { Header } from './components/Header';
import { AccountList } from './components/AccountList';
import { TransferForm } from './components/TransferForm';
import { SignInScreen } from './components/SignInScreen';
import { useAuth } from './auth/AuthContext';
import { getAccounts } from './api/client';
import type { Account } from './api/types';
import './App.css';

export function App() {
  const { user, loading: authLoading } = useAuth();

  const [accounts, setAccounts] = useState<Account[]>([]);
  const [accountsLoading, setAccountsLoading] = useState<boolean>(false);
  const [accountsError, setAccountsError] = useState<string | null>(null);

  const loadAccounts = useCallback(async () => {
    setAccountsLoading(true);
    setAccountsError(null);
    try {
      const data = await getAccounts();
      setAccounts(data);
    } catch (e) {
      setAccountsError(e instanceof Error ? e.message : 'Unknown error');
    } finally {
      setAccountsLoading(false);
    }
  }, []);

  // Load accounts once a user becomes available.
  useEffect(() => {
    if (user) {
      loadAccounts();
    }
  }, [user, loadAccounts]);

  return (
    <div className="app">
      <Header />
      <main>
        {authLoading && <p className="status-message">Checking sign-in state...</p>}
        {!authLoading && !user && <SignInScreen />}
        {!authLoading && user && (
          <>
            <AccountList accounts={accounts} loading={accountsLoading} error={accountsError} />
            <TransferForm accounts={accounts} onTransferComplete={loadAccounts} />
          </>
        )}
      </main>
    </div>
  );
}
