/**
 * API client for the banking backend.
 *
 * All HTTP communication with the backend goes through this file.
 * Requests use same-origin URLs that the Vite proxy forwards to the
 * BFF on port 8080.
 */

import type { Account, TransferRequest, TransferResponse, User } from './types';
import { readCookie } from '../utils/cookies';

export async function getCurrentUser(): Promise<User | null> {
  const response = await fetch('/api/me', {
    headers: { Accept: 'application/json' },
  });
  if (response.status === 401) {
    return null; // not logged in
  }
  if (!response.ok) {
    throw new Error(`Failed to load user: ${response.status}`);
  }
  return response.json();
}

export async function getAccounts(): Promise<Account[]> {
  const response = await fetch('/api/accounts', {
    headers: { Accept: 'application/json' },
  });
  if (!response.ok) {
    throw new Error(`Failed to load accounts: ${response.status}`);
  }
  return response.json();
}

export async function postTransfer(request: TransferRequest): Promise<TransferResponse> {
  const response = await fetch('/api/transfers', {
    method: 'POST',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
      ...csrfHeader(),
    },
    body: JSON.stringify(request),
  });
  if (!response.ok) {
    const message = await safeReadErrorMessage(response);
    throw new Error(message || `Transfer failed: ${response.status}`);
  }
  return response.json();
}

export function logout(): void {
  const token = readCookie('XSRF-TOKEN');

  // Build and submit a form so the browser navigates through the
  // logout redirect chain (BFF -> auth server -> back to /).
  // fetch() cannot follow cross-origin HTML redirects, which is why
  // we use a real form submission here instead.
  const form = document.createElement('form');
  form.method = 'POST';
  form.action = '/logout';
  form.style.display = 'none';

  if (token) {
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = '_csrf';
    input.value = token;
    form.appendChild(input);
  }

  document.body.appendChild(form);
  form.submit();
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

function csrfHeader(): Record<string, string> {
  const token = readCookie('XSRF-TOKEN');
  return token ? { 'X-XSRF-TOKEN': token } : {};
}
