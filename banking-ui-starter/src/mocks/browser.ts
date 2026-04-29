/**
 * Mock Service Worker browser setup.
 *
 * This file creates the MSW worker instance and wires it up with the
 * request handlers. The worker is started in src/main.tsx before the
 * React application mounts.
 */

import { setupWorker } from 'msw/browser';
import { handlers } from './handlers';

export const worker = setupWorker(...handlers);
