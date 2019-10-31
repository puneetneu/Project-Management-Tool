import React from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Header from "./components/Layout/Header";

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Dashboard />
      </div>
    </Router>
  );
}

export default App;
