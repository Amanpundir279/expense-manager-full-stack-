import { useState } from "react";
import { updateExpense } from "../api/expenseApi";

const EditExpense = ({ expense, onCancel, onSuccess }) => {
  const [amount, setAmount] = useState(expense.amount);
  const [description, setDescription] = useState(expense.description);
  const [expenseDate, setExpenseDate] = useState(expense.expenseDate);

  const handleSubmit = async (e) => {
    e.preventDefault();

    await updateExpense(expense.id, {
      amount,
      description,
      expenseDate,
    });

    onSuccess();
    onCancel();
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: 20 }}>
      <h3>Edit Expense</h3>

      <input
        type="number"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
        required
      />
      <br /><br />

      <input
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
      />
      <br /><br />

      <input
        type="date"
        value={expenseDate}
        onChange={(e) => setExpenseDate(e.target.value)}
        required
      />
      <br /><br />

      <button type="submit">Update</button>
      <button type="button" onClick={onCancel} style={{ marginLeft: 10 }}>
        Cancel
      </button>
    </form>
  );
};

export default EditExpense;