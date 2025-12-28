import { useEffect, useState } from "react";
import { getExpenses, deleteExpense } from "../api/expenseApi";
import AddExpense from "../components/AddExpense";
import EditExpense from "../components/EditExpense";
import ExpenseChart from "../components/ExpenseChart";
import { logout } from "../auth/logout";
import { useNavigate } from "react-router-dom";
import "../styles/expenses.css";

const Expenses = () => {
  const [expenses, setExpenses] = useState([]);
  const [page, setPage] = useState(0);
  const [lastPage, setLastPage] = useState(false);
  const [editingExpense, setEditingExpense] = useState(null);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();
  const username = localStorage.getItem("username");

  // 🔐 Auth guard
  useEffect(() => {
    if (!localStorage.getItem("token")) {
      navigate("/login", { replace: true });
    }
  }, [navigate]);

  const loadExpenses = () => {
    setLoading(true);
    getExpenses(page)
      .then((res) => {
        setExpenses(res.data.content || []);
        setLastPage(res.data.last);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    loadExpenses();
  }, [page]);

  // 📊 Summary
  const totalAmount = expenses.reduce(
    (sum, e) => sum + Number(e.amount || 0),
    0
  );

  return (
    <div className="page">
      {/* Header */}
      <div className="header">
        <div>
          <h1>My Expenses</h1>
          <p className="subtitle">Track your daily spending</p>
        </div>

        <button
          className="logout-btn"
          onClick={() => {
            logout();
            navigate("/login", { replace: true });
          }}
        >
          Logout
        </button>
      </div>

      <p className="welcome">
        Welcome, <strong>{username}</strong> 👋
      </p>

      {/* Summary */}
      <div className="summary">
        <div className="summary-card">
          <h4>Total Spent</h4>
          <p>₹{totalAmount}</p>
        </div>

        <div className="summary-card secondary">
          <h4>Total Expenses</h4>
          <p>{expenses.length}</p>
        </div>
      </div>

      {/* Add Expense */}
      <AddExpense onSuccess={loadExpenses} />

      {/* Edit Modal */}
      {editingExpense && (
        <EditExpense
          expense={editingExpense}
          onCancel={() => setEditingExpense(null)}
          onSuccess={() => {
            setEditingExpense(null);
            loadExpenses();
          }}
        />
      )}

         {/* Chart */}
     {expenses.length > 0 && (
    <div className="card chart-card">
    <h3 className="chart-title">Expense Breakdown</h3>
    <div className="chart-wrapper">
      <ExpenseChart expenses={expenses} />
    </div>
  </div>
)}

      {/* Expense List */}
      <div className="card">
        {loading ? (
          <p className="loading">Loading expenses...</p>
        ) : expenses.length === 0 ? (
          <p className="empty">No expenses added yet 💸</p>
        ) : (
          <ul className="expense-list">
            {expenses.map((e) => (
              <li key={e.id} className="expense-item">
                <div className="left">
                  <div className="title">
                    ₹{e.amount} — {e.description}
                  </div>

                  <div className="meta">
                    {e.categoryName && (
                      <span
                        className={`category-badge ${e.categoryName.toLowerCase()}`}
                      >
                        {e.categoryName}
                      </span>
                    )}
                    <span className="date">{e.expenseDate}</span>
                  </div>
                </div>

                <div className="actions">
                  <button
                    className="edit-btn"
                    onClick={() => setEditingExpense(e)}
                  >
                    Edit
                  </button>

                  <button
                    className="delete-btn"
                    onClick={async () => {
                      if (!window.confirm("Delete this expense?")) return;
                      await deleteExpense(e.id);
                      loadExpenses();
                    }}
                  >
                    Delete
                  </button>
                </div>
              </li>
            ))}
          </ul>
        )}

        {/* Pagination */}
        <div className="pagination">
          <button
            disabled={loading || page === 0}
            onClick={() => setPage((p) => p - 1)}
          >
            Previous
          </button>

          <button
            disabled={loading || lastPage}
            onClick={() => setPage((p) => p + 1)}
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
};

export default Expenses;