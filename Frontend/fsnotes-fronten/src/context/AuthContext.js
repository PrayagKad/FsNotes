import React, { createContext, useState } from "react";
import * as authService from "../api/authService";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(authService.getCurrentUser());

  // Login function
  const login = async (username, password) => {
    const data = await authService.login({ username, password });
    if (data.token) {
      localStorage.setItem("fsnotes-token", data.token);
      localStorage.setItem("fsnotes-username", data.username);
      setUser(data.username);
    }
    return data;
  };

  // Register function (auto-login after successful register)
  const register = async (username, password) => {
    // Register user; do NOT auto-login. Let user sign in explicitly afterwards.
    await authService.register({ username, password });
  };

  // Logout function
  const logout = () => {
    authService.logout();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
