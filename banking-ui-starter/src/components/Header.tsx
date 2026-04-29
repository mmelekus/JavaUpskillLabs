/**
 * Header component.
 *
 * Renders the application's title bar at the top of every page. This
 * component receives no props in Lab A. In a later lab it will receive
 * a logged-in user object and display the user's name plus a logout
 * button.
 */

export function Header() {
  return (
    <header className="header">
      <h1>MD282 Bank</h1>
      <p className="tagline">Online Banking</p>
    </header>
  );
}
