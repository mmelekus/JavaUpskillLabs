/**
 * Header component.
 *
 * Reads the current user from the auth context. When a user is logged in,
 * shows their name and a Sign-out button. When no user is logged in,
 * shows nothing extra (the Sign-in button lives in SignInScreen).
 */

import { useAuth } from '../auth/AuthContext';
import { logout as logoutApi } from '../api/client';

export function Header() {
  const { user } = useAuth();

  function handleSignOut() {
    logoutApi();
    // No refresh needed: the form submission causes a full page navigation,
    // and the app reloads with no session, which renders the Welcome screen.
  }

  return (
    <header className="header">
      <div className="header-content">
        <div>
          <h1>MD282 Bank</h1>
          <p className="tagline">Online Banking</p>
        </div>
        {user && (
          <div className="header-user">
            <span className="user-name">Hello, {user.name}</span>
            <button type="button" onClick={handleSignOut} className="sign-out-button">
              Sign out
            </button>
          </div>
        )}
      </div>
    </header>
  );
}
