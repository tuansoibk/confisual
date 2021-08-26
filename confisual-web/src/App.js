import logo from './logo.svg';
import './App.css';
import { Counter } from "./features/counter/Counter";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
          <Counter>
          </Counter>
      </header>
    </div>
  );
}

export default App;
