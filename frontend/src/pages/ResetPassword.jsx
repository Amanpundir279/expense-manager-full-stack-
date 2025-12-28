import { useSearchParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import authApi from "../api/authApi";
import "../styles/auth.css";

export default function ResetPassword() {
  const [params] = useSearchParams();
  const navigate = useNavigate();
  const token = params.get("token");

  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleReset = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      await authApi.post("/reset-password", {
        token,
        password,
      });

      setMessage("✅ Password reset successful");
      setTimeout(() => navigate("/login"), 2000);
    } catch (err) {
      setMessage("❌ Invalid or expired token");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-card" onSubmit={handleReset}>
        <h2>Reset Password 🔑</h2>
        <p className="subtitle">Enter your new password</p>

        {message && <div className="info">{message}</div>}

        <input
          type="password"
          placeholder="New password"
          required
          minLength={6}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button disabled={loading}>
          {loading ? "Resetting..." : "Reset Password"}
        </button>
      </form>
    </div>
  );
}