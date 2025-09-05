import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Navbar from "../components/NavBar";

const ViewNotePage = () => {
  const { sharablelink } = useParams(); // get the link from URL
  const [note, setNote] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    // Fetch note by sharable link from backend
    fetch(`${process.env.REACT_APP_API_URL || "http://localhost:8080"}/notes/share/${sharablelink}`)
      .then((res) => {
        if (!res.ok) throw new Error("Note not found");
        return res.json();
      })
      .then((data) => {
        setNote(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [sharablelink]);

  if (loading) return (
    <div>
      <Navbar />
      <div className="container" style={{ padding: "40px 20px" }}>
        <div className="loading">
          <div style={{ fontSize: "48px", marginBottom: "16px" }}>⏳</div>
          <p>Loading note...</p>
        </div>
      </div>
    </div>
  );
  
  if (error) return (
    <div>
      <Navbar />
      <div className="container" style={{ padding: "40px 20px" }}>
        <div className="error">
          <h3>Error: {error}</h3>
          <p>The note you're looking for might not exist or the link might be invalid.</p>
        </div>
      </div>
    </div>
  );

  return (
    <div>
      <Navbar />
      <div className="container" style={{ padding: "40px 20px" }}>
        <div style={{ maxWidth: "800px", margin: "0 auto" }}>
          <div className="card fade-in" style={{ 
            background: "white", 
            borderRadius: "16px", 
            boxShadow: "0 8px 32px rgba(0, 0, 0, 0.1)",
            border: "1px solid #e9ecef",
            overflow: "hidden"
          }}>
            {/* Header Section */}
            <div style={{ 
              background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)", 
              padding: "30px", 
              color: "white",
              textAlign: "center"
            }}>
              <h1 style={{ 
                fontSize: "32px", 
                fontWeight: "700", 
                margin: "0 0 8px 0",
                textShadow: "0 2px 4px rgba(0,0,0,0.1)"
              }}>
                {note.title}
              </h1>
              <div style={{ 
                fontSize: "14px", 
                opacity: "0.9",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                gap: "8px"
              }}>
                <span>📅</span>
                <span>Created: {new Date(note.createdAt).toLocaleString()}</span>
              </div>
            </div>
            
            {/* Content Section */}
            <div style={{ padding: "40px" }}>
              <div style={{ 
                fontSize: "18px", 
                lineHeight: "1.8", 
                color: "#2c3e50",
                whiteSpace: "pre-wrap",
                wordWrap: "break-word",
                fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif"
              }}>
                {note.content}
              </div>
            </div>
            
            {/* Footer Section */}
            <div style={{ 
              background: "#f8f9fa", 
              padding: "20px 40px", 
              borderTop: "1px solid #e9ecef",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center"
            }}>
              <div style={{ 
                fontSize: "14px", 
                color: "#6c757d",
                display: "flex",
                alignItems: "center",
                gap: "8px"
              }}>
                <span>🔗</span>
                <span>Shared Note</span>
              </div>
              <div style={{ 
                fontSize: "12px", 
                color: "#adb5bd"
              }}>
                Powered by FsNotes
              </div>
            </div>
          </div>
          
          {/* Back to App Button */}
          <div style={{ textAlign: "center", marginTop: "30px" }}>
            <a 
              href="/" 
              style={{
                display: "inline-block",
                padding: "12px 24px",
                background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
                color: "white",
                textDecoration: "none",
                borderRadius: "8px",
                fontWeight: "600",
                transition: "all 0.2s ease",
                boxShadow: "0 4px 15px rgba(102, 126, 234, 0.3)"
              }}
              onMouseOver={(e) => {
                e.target.style.transform = "translateY(-2px)";
                e.target.style.boxShadow = "0 6px 20px rgba(102, 126, 234, 0.4)";
              }}
              onMouseOut={(e) => {
                e.target.style.transform = "translateY(0)";
                e.target.style.boxShadow = "0 4px 15px rgba(102, 126, 234, 0.3)";
              }}
            >
              ← Back to FsNotes
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewNotePage;
