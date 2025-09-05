import React from "react";
import { Link } from "react-router-dom";
import LoginForm from "../components/LoginForm";

const LoginPage = () => {
  return (
    <div style={{ minHeight: "100vh", display: "flex", alignItems: "center", justifyContent: "center", padding: "20px" }}>
      <div className="card" style={{ width: "100%", maxWidth: "400px" }}>
        <div className="text-center mb-3">
          <h1 style={{ fontSize: "28px", marginBottom: "8px", background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)", WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent" }}>
            Welcome To FsNotes
          </h1>
          <p className="text-muted">Sign in to your account</p>
        </div>
        
        <LoginForm />
        
        <div className="text-center mt-3">
          <p className="text-muted mb-2">Don't have an account?</p>
          <div style={{ display: "flex", gap: "12px", justifyContent: "center", flexWrap: "wrap" }}>
            <Link to="/register" className="btn btn-primary">
              Create Account
            </Link>
            <Link to="/public" className="btn btn-success">
              View Shared Note
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
