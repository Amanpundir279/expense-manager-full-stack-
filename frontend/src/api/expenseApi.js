import axios from "./axios";

export const getExpenses = (page = 0, size = 5) => {
  return axios.get(`/expenses?page=${page}&size=${size}`);
};

export const createExpense = async (expense) => {
  const res = await axios.post("/expenses", expense);
  return res.data;
};

export const updateExpense = async (id, expense) => {
  const res = await axios.put(`/expenses/${id}`, expense);
  return res.data;
};

export const deleteExpense = async (id) => {
  return axios.delete(`/expenses/${id}`);
};