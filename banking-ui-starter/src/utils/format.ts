/**
 * Currency formatting helper.
 *
 * Uses the browser's built-in Intl.NumberFormatt API to produce
 * locale-aware currency strings such as "$1,234.56".
 */

export function formatCurrency(amount: number): string {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
  }).format(amount);
}
