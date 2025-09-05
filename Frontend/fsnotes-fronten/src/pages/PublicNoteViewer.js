import React, { useState } from "react";
import { getNoteByLink } from "../api/noteService";
import Navbar from "../components/NavBar";

const PublicNoteViewer = () => {
  const [link, setLink] = useState("");
  const [note, setNote] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleViewNote = async (e) => {
    e.preventDefault();
    if (!link.trim()) return;

    setLoading(true);
    setError("");
    setNote(null);

    try {
      // Extract the link from URL if full URL is provided
      let noteLink = link.trim();
      if (noteLink.includes('/view/')) {
        noteLink = noteLink.split('/view/')[1];
      }
      if (noteLink.includes('/share/')) {
        noteLink = noteLink.split('/share/')[1];
      }

      const noteData = await getNoteByLink(noteLink);
      setNote(noteData);
    } catch (err) {
      setError("Note not found. Please check the link and try again.");
      console.error("Error fetching note:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <Navbar />
      <div className="container" style={{ padding: "40px 20px" }}>
        <div style={{ maxWidth: "600px", margin: "0 auto" }}>
          <div className="card text-center mb-3">
            <h1 style={{ fontSize: "32px", marginBottom: "8px", background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)", WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent" }}>
              📖 View Shared Note
            </h1>
            <p className="text-muted">Enter a sharable link to view a note without logging in</p>
          </div>
          
          <div className="card">
            <form onSubmit={handleViewNote}>
              <div className="form-group">
                <label className="form-label">Sharable Link</label>
                <input
                  type="text"
                  className="form-input"
                  placeholder="Paste the sharable link here..."
                  value={link}
                  onChange={(e) => setLink(e.target.value)}
                />
              </div>
              
              <button 
                type="submit" 
                disabled={loading || !link.trim()}
                className="btn btn-primary"
                style={{ width: "100%" }}
              >
                {loading ? "Loading..." : "View Note"}
              </button>
            </form>

            {error && <div className="error">{error}</div>}
          </div>

          {note && (
            <div className="note-card fade-in">
              <h2 className="note-title">{note.title}</h2>
              <div className="note-content" style={{ whiteSpace: "pre-wrap" }}>
                {note.content}
              </div>
              <div className="note-meta">
                Created: {new Date(note.createdAt).toLocaleString()}
              </div>
            </div>
          )}

          <div className="card" style={{ backgroundColor: "#f8f9fa" }}>
            <h3 style={{ marginBottom: "16px", color: "#2c3e50" }}>How to use:</h3>
            <ol style={{ paddingLeft: "20px", lineHeight: "1.8" }}>
              <li>Get a sharable link from someone who shared a note with you</li>
              <li>Paste the link in the input field above</li>
              <li>Click "View Note" to see the content</li>
            </ol>
            <p style={{ marginTop: "16px", fontWeight: "600", color: "#28a745" }}>
              ✨ You don't need to create an account to view shared notes!
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PublicNoteViewer;
