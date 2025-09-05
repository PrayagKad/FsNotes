import axios from "axios";

const API_URL = `${process.env.REACT_APP_API_URL || "http://localhost:8080"}/auth`;

// Register a new user
export const register = async (userData) => {
  return axios.post(`${API_URL}/register`, userData, {
    headers: {
      "Content-Type": "application/json",
    },
  });
};

// Login user and get JWT token
export const login = async (credentials) => {
  const response = await axios.post(`${API_URL}/login`, credentials, {
    headers: { "Content-Type": "application/json" },
  });
  return response.data; // { token, username }
};

// Logout user
export const logout = () => {
  localStorage.removeItem("fsnotes-token");
  localStorage.removeItem("fsnotes-username");
};

// Get currently logged-in user
export const getCurrentUser = () => {
  return localStorage.getItem("fsnotes-username");
};

// Get token
export const getToken = () => {
  return localStorage.getItem("fsnotes-token");
};
