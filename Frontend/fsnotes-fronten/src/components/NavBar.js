import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useContext(AuthContext);

  return (
    <header className="header">
      <div className="container">
        <div className="header-content">
          <Link to="/" style={{ textDecoration: "none" }}>
            <div className="logo">📝 FsNotes</div>
          </Link>
          {user ? (
            <div style={{ display: "flex", alignItems: "center", gap: "16px" }}>
              <span style={{ color: "#6c757d", fontSize: "14px" }}>
                Welcome, <strong>{user}</strong>!
              </span>
              <button onClick={logout} className="btn btn-secondary btn-small">
                Logout
              </button>
            </div>
          ) : (
            <div style={{ display: "flex", gap: "12px" }}>
              <Link to="/login" className="btn btn-secondary">
                Login
              </Link>
              <Link to="/register" className="btn btn-primary">
                Register
              </Link>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};

export default Navbar;
