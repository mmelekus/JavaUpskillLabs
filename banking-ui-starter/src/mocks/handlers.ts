/**
 * Mock Service Worker request handlers.
 *
 * These handlers intercept the fetch calls from the React application and
 * return canned responses. The data lives in this file; mutations from
 * POST requests update the in-memory data so that subsequent GETs reflect
 * the change.
 *
 * In a later lab, these handlers will be removed and the React application
 * will talk to a real Spring Boot BFF.
 */

import { http, HttpResponse, delay } from 'msw';
import type {
  Account,
  TransferRequest,
  TransferResponse,
  Transaction,
} from '../api/types';

// In-memory data store. Mutated by POST /api/transfers.

let accounts: Account[] = [
  { accountNumber: 'ACC-001', status: 'ACTIVE',   balance: 1500.0,  type: 'CHECKING' },
  { accountNumber: 'ACC-002', status: 'ACTIVE',   balance: 8200.5,  type: 'SAVINGS'  },
  { accountNumber: 'ACC-003', status: 'ACTIVE',   balance: 3100.75, type: 'SAVINGS'  },
  { accountNumber: 'ACC-004', status: 'INACTIVE', balance: 0,       type: 'CHECKING' },
];

const transactions: Transaction[] = [];

function nextTransactionId(): string {
  return `TXN-${(transactions.length + 1).toString().padStart(4, '0')}`;
}

export const handlers = [
  // GET /api/accounts -- returns all accounts.
  http.get('/api/accounts', async () => {
    await delay(300);
    return HttpResponse.json(accounts);
  }),

  // POST /api/transfers -- moves funds from one account to another.
  http.post('/api/transfers', async ({ request }) => {
    await delay(500);

    const body = (await request.json()) as TransferRequest;

    const fromAccount = accounts.find(
      (a) => a.accountNumber === body.fromAccountNumber
    );
    const toAccount = accounts.find(
      (a) => a.accountNumber === body.toAccountNumber
    );

    if (!fromAccount) {
      return HttpResponse.json(
        { message: `Source account ${body.fromAccountNumber} not found` },
        { status: 404 }
      );
    }

    if (fromAccount.status !== 'ACTIVE') {
      return HttpResponse.json(
        { message: `Source account ${fromAccount.accountNumber} is not active` },
        { status: 400 }
      );
    }

    if (body.amount <= 0) {
      return HttpResponse.json(
        { message: 'Transfer amount must be greater than zero' },
        { status: 400 }
      );
    }

    if (body.amount > fromAccount.balance) {
      return HttpResponse.json(
        { message: 'Insufficient funds' },
        { status: 400 }
      );
    }

    // Apply the transfer to source.
    accounts = accounts.map((a) => {
      if (a.accountNumber === fromAccount.accountNumber) {
        return { ...a, balance: a.balance - body.amount };
      }
      // If the destination is one of our accounts, credit it.
      if (toAccount && a.accountNumber === toAccount.accountNumber) {
        return { ...a, balance: a.balance + body.amount };
      }
      return a;
    });

    const transactionId = nextTransactionId();

    transactions.push({
      transactionId,
      date: new Date().toISOString(),
      type: 'TRANSFER',
      amount: body.amount,
      account1: body.fromAccountNumber,
      account2: body.toAccountNumber,
      status: 'COMPLETE',
    });

    const response: TransferResponse = {
      transactionId,
      status: 'COMPLETE',
    };

    return HttpResponse.json(response);
  }),
];
