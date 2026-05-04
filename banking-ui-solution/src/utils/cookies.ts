/**
 * Read a cookie value by name.
 *
 * Browsers expose cookies as a single semicolon-separated string in
 * document.cookie. This helper splits the string and returns the
 * value of the named cookie (URL-decoded), or null if not present.
 */

export function readCookie(name: string): string | null {
  const cookies = document.cookie.split(';');
  for (const cookie of cookies) {
    const [key, value] = cookie.trim().split('=');
    if (key === name) {
      return decodeURIComponent(value);
    }
  }
  return null;
}
