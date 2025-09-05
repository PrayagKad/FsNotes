import React, { useContext } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider, AuthContext } from "./context/AuthContext";
import LoginPage from "./pages/LoginPage";
import RegistrationPage from "./pages/RegistrationPage";
import Homepage from "./pages/Homepage";
import ViewNotePage from "./pages/ViewNotePage";
import PublicNoteViewer from "./pages/PublicNoteViewer";
// import "./GlobalStyles.css";

// Protected route to restrict access to authenticated users
const ProtectedRoute = ({ children }) => {
  const { user } = useContext(AuthContext);
  if (!user) return <Navigate to="/login" />; // redirect to login if not logged in
  return children;
};

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          {/* Public routes */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegistrationPage />} />
          <Route path="/view/:sharablelink" element={<ViewNotePage />} />
          <Route path="/public" element={<PublicNoteViewer />} />

          {/* Protected route */}
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Homepage />
              </ProtectedRoute>
            }
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
