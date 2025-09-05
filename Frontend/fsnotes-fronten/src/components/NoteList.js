import React from "react";

const NoteList = ({ notes, onEdit, onDelete }) => {

  const copyLink = (link) => {
    navigator.clipboard.writeText(link)
      .then(() => alert("Sharable link copied!"))
      .catch(err => console.error("Failed to copy link:", err));
  };

  return (
    <div>
      {(!notes || notes.length === 0) ? (
        <div className="card text-center">
          <div style={{ padding: "40px" }}>
            <div style={{ fontSize: "48px", marginBottom: "16px" }}>📝</div>
            <h3 style={{ color: "#6c757d", marginBottom: "8px" }}>No notes yet</h3>
            <p className="text-muted">Create your first note to get started!</p>
          </div>
        </div>
      ) : (
        <div>
          <h2 style={{ marginBottom: "20px", color: "#2c3e50" }}>Your Notes</h2>
          {notes.map((note) => (
            <div key={note.id} className="note-card fade-in">
              <h3 className="note-title">{note.title}</h3>
              <div className="note-content">{note.content}</div>
              {note.createdAt && (
                <div className="note-meta">
                  Created: {new Date(note.createdAt).toLocaleString()}
                </div>
              )}
              <div className="note-actions">
                <button onClick={() => onEdit(note)} className="btn btn-secondary btn-small">
                  ✏️ Edit
                </button>
                <button onClick={() => onDelete(note.id)} className="btn btn-danger btn-small">
                  🗑️ Delete
                </button>
                {note.sharablelink && (
                  <button 
                    onClick={() => copyLink(`${window.location.origin}/view/${note.sharablelink}`)}
                    className="btn btn-success btn-small"
                  >
                    🔗 Copy Link
                  </button>
                )}
                {note.sharablelink && (
                  <a 
                    href={`${window.location.origin}/view/${note.sharablelink}`} 
                    target="_blank" 
                    rel="noopener noreferrer"
                    className="btn btn-primary btn-small"
                  >
                    👁️ View
                  </a>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default NoteList;
