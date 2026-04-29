/**
 * App component.
 *
 * The root component of the application. In Exercise 1 it just renders
 * the Header and AccountList. In Exercise 3 you will refactor it to
 * own the accounts state and pass it down to AccountList and
 * TransferForm.
 */

import { Header } from './components/Header';
// TODO 1.5: Import the AccountList component from './components/AccountList'
import { AccountList } from './components/AccountList';
import './App.css';

export function App() {
  return (
    <div className="app">
      <Header />
      <main>
        {/* TODO 1.6: Render the AccountList component here */}
        <AccountList />
      </main>
    </div>
  );
}
