import { useEffect, useState } from "react";
import { createExpense } from "../api/expenseApi";
import { getCategories } from "../api/categoryApi";
import "../styles/expenses.css";

const AddExpense = ({ onSuccess }) => {
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [expenseDate, setExpenseDate] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    getCategories().then(res => setCategories(res.data));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    await createExpense({
      amount: Number(amount),
      description,
      expenseDate,
      categoryId: categoryId || null
    });

    setAmount("");
    setDescription("");
    setExpenseDate("");
    setCategoryId("");

    onSuccess();
  };

  return (
    <div className="card">
      <h3>Add Expense</h3>

      <form onSubmit={handleSubmit}>
        <div className="form-row">
          <input
            placeholder="Amount"
            value={amount}
            onChange={e => setAmount(e.target.value)}
            required
          />
          <input
            placeholder="Description"
            value={description}
            onChange={e => setDescription(e.target.value)}
            required
          />
        </div>

        <div className="form-row">
          <input
            type="date"
            value={expenseDate}
            onChange={e => setExpenseDate(e.target.value)}
            required
          />

          <select
            value={categoryId}
            onChange={e => setCategoryId(e.target.value)}
          >
            <option value="">No Category</option>
            {categories.map(c => (
              <option key={c.id} value={c.id}>{c.name}</option>
            ))}
          </select>

          <button className="primary-btn">Add</button>
        </div>
      </form>
    </div>
  );
};

export default AddExpense;