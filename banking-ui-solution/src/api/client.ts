/**
 * API client for the banking backend.
 *
 * All HTTP communication with the backend goes through this file. During
 * Lab A the requests are intercepted by Mock Service Worker (MSW) and
 * answered with canned data. In a later lab MSW will be removed and these
 * same calls will hit a real Spring Boot BFF without changing the
 * function signatures.
 */

import type { Account, TransferRequest, TransferResponse } from './types';

export async function getAccounts(): Promise<Account[]> {
  const response = await fetch('/api/accounts');
  if (!response.ok) {
    throw new Error(`Failed to load accounts: ${response.status}`);
  }
  return response.json();
}

export async function postTransfer(
  request: TransferRequest
): Promise<TransferResponse> {
  const response = await fetch('/api/transfers', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(request),
  });
  if (!response.ok) {
    const message = await safeReadErrorMessage(response);
    throw new Error(message || `Transfer failed: ${response.status}`);
  }
  return response.json();
}

async function safeReadErrorMessage(response: Response): Promise<string | null> {
  try {
    const body = await response.json();
    if (body && typeof body.message === 'string') {
      return body.message;
    }
    return null;
  } catch {
    return null;
  }
}
