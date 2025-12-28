import axios from "axios";

const authApi = axios.create({
  baseURL: "http://localhost:8080/api/auth",
});

export const login = (data) => authApi.post("/login", data);
export const forgotPassword = (username) =>
  authApi.post("/forgot-password", { username });

export const resetPassword = (token, password) =>
  authApi.post("/reset-password", { token, password });

export default authApi;