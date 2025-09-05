import React, { useState, useEffect } from "react";

const NoteForm = ({ onSave, initialData }) => {
  const [title, setTitle] = useState(initialData?.title || "");
  const [content, setContent] = useState(initialData?.content || "");

  // Reset form when initialData changes (for editing)
  useEffect(() => {
    if (initialData) {
      setTitle(initialData.title || "");
      setContent(initialData.content || "");
    } else {
      setTitle("");
      setContent("");
    }
  }, [initialData]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (title.trim() && content.trim()) {
      onSave({ title, content });
      setTitle("");
      setContent("");
    }
  };

  return (
    <div className="card">
      <h3 style={{ marginBottom: "20px", color: "#2c3e50" }}>
        {initialData ? "Edit Note" : "Create New Note"}
      </h3>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Title</label>
          <input
            type="text"
            className="form-input"
            placeholder="Enter note title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        
        <div className="form-group">
          <label className="form-label">Content</label>
          <textarea
            className="form-textarea"
            placeholder="Write your note content here..."
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>
        
        <button type="submit" className="btn btn-primary">
          {initialData ? "Update Note" : "Create Note"}
        </button>
      </form>
    </div>
  );
};

export default NoteForm;
