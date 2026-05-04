/**
 * TypeScript types for the banking API.
 */

export type AccountStatus = 'ACTIVE' | 'INACTIVE';
export type AccountType = 'SAVINGS' | 'CHECKING';

export type Account = {
  accountNumber: string;
  status: AccountStatus;
  balance: number;
  type: AccountType;
};

export type Customer = {
  customerId: string;
  name: string;
  email: string;
};

export type TransactionStatus = 'COMPLETE' | 'FAILED';
export type TransactionType = 'TRANSFER' | 'DEPOSIT' | 'WITHDRAWAL';

export type Transaction = {
  transactionId: string;
  date: string;
  type: TransactionType;
  amount: number;
  account1: string;
  account2: string | null;
  status: TransactionStatus;
};

export type TransferRequest = {
  fromAccountNumber: string;
  toAccountNumber: string;
  amount: number;
};

export type TransferResponse = {
  transactionId: string;
  status: TransactionStatus;
};

// New: matches the UserInfoDto returned by the BFF's /api/me endpoint.
export type User = {
  username: string;
  name: string;
  roles: string[];
};
