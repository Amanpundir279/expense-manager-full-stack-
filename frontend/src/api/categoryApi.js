import axios from "./axios";

export const getCategories = () => {
  return axios.get("/categories");
};

export const createCategory = (name) => {
  return axios.post("/categories", { name });
};