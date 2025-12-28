import { useState } from "react";
import authApi from "../api/authApi";
import { Link } from "react-router-dom";
import "../styles/auth.css";

export default function ForgotPassword() {
  const [username, setUsername] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      await authApi.post("/forgot-password", { username });
      setMessage("✅ Reset link sent to your email");
    } catch (err) {
      setMessage("❌ User not found or error occurred");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-card" onSubmit={handleSubmit}>
        <h2>Forgot Password 🔐</h2>
        <p className="subtitle">Enter your username to receive a reset link</p>

        {message && <div className="info">{message}</div>}

        <input
          type="text"
          placeholder="Username"
          value={username}
          required
          onChange={(e) => setUsername(e.target.value)}
        />

        <button disabled={loading}>
          {loading ? "Sending..." : "Send Reset Link"}
        </button>

        <Link className="link" to="/login">
          ← Back to login
        </Link>
      </form>
    </div>
  );
}