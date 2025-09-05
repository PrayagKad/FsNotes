import React from "react";
import RegistrationForm from "../components/RegistrationForm";
import { Link } from "react-router-dom";

const RegistrationPage = () => {
  return (
    <div style={{ minHeight: "100vh", display: "flex", alignItems: "center", justifyContent: "center", padding: "20px" }}>
      <div className="card" style={{ width: "100%", maxWidth: "400px" }}>
        <div className="text-center mb-3">
          <h1 style={{ fontSize: "28px", marginBottom: "8px", background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)", WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent" }}>
            Join FsNotes
          </h1>
          <p className="text-muted">Create your account to get started</p>
        </div>
        
        <RegistrationForm />
        
        <div className="text-center mt-3">
          <p className="text-muted">
            Already have an account? <Link to="/login" style={{ color: "#667eea", textDecoration: "none", fontWeight: "600" }}>Sign In</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default RegistrationPage;
