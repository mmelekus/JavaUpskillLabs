/**
 * App component (completed solution).
 *
 * Owns the accounts state and shares it between AccountList (read-only
 * display) and TransferForm (write actions). After a successful transfer
 * the form invokes loadAccounts to refresh the displayed balances.
 */

import { useState, useEffect, useCallback } from 'react';
import { Header } from './components/Header';
import { AccountList } from './components/AccountList';
import { TransferForm } from './components/TransferForm';
import { getAccounts } from './api/client';
import type { Account } from './api/types';
import './App.css';

export function App() {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadAccounts = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getAccounts();
      setAccounts(data);
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Unknown error');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadAccounts();
  }, [loadAccounts]);

  return (
    <div className="app">
      <Header />
      <main>
        <AccountList
          accounts={accounts}
          loading={loading}
          error={error}
        />
        <TransferForm
          accounts={accounts}
          onTransferComplete={loadAccounts}
        />
      </main>
    </div>
  );
}
