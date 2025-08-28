import { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from "react-router-dom";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Dashboard from "./pages/DashBoard";

export default function App() {
  const [token, setToken] = useState(null);

  return (
    <Router>
      <nav style={{ padding: "1rem", borderBottom: "1px solid #ccc" }}>
        <Link to="/" style={{ marginRight: "1rem" }}>Home</Link>
        {!token && <Link to="/login" style={{ marginRight: "1rem" }}>Login</Link>}
        {!token && <Link to="/register">Register</Link>}
        {token && <Link to="/dashboard" style={{ marginLeft: "1rem" }}>Dashboard</Link>}
      </nav>

      <Routes>
        <Route
          path="/"
          element={
            <div style={{ padding: "2rem" }}>
              <h1>Welcome to Metaphysical App</h1>
              {token ? <p>You are logged in!</p> : <p>Please log in or register.</p>}
            </div>
          }
        />
        <Route path="/login" element={<Login setToken={setToken} />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/dashboard"
          element={
            token ? <Dashboard token={token} /> : <Navigate to="/login" />
          }
        />
      </Routes>
    </Router>
  );
}
