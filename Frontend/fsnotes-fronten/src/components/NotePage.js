import React, { useState, useEffect } from "react";
import NoteList from "./NoteList";
import { getNotes, createNote, deleteNote, updateNote } from "../api/noteService";

const NotesPage = () => {
  const [notes, setNotes] = useState([]);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  // Fetch existing notes on component mount
  useEffect(() => {
    const fetchNotes = async () => {
      try {
        const data = await getNotes();
        setNotes(data);
      } catch (err) {
        console.error("Failed to fetch notes:", err);
      }
    };
    fetchNotes();
  }, []);

  // Add new note
  const handleAddNote = async (e) => {
    e.preventDefault();

    try {
      // Backend uses JWT auth to resolve creator; no need to send creator object
      const newNote = await createNote({
        title,
        content
      });

      // Add newly saved note to state
      setNotes([...notes, newNote]);

      // Clear the form for next note
      setTitle("");
      setContent("");
    } catch (err) {
      console.error("Failed to add note:", err);
      alert("Failed to add note. Check console for details.");
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteNote(id);
      setNotes(notes.filter((note) => note.id !== id));
    } catch (err) {
      console.error("Failed to delete note:", err);
    }
  };

  const handleEdit = async (note) => {
    const updatedContent = prompt("Edit content:", note.content);
    if (updatedContent !== null) {
      try {
        const updatedNote = await updateNote(note.id, { ...note, content: updatedContent });
        setNotes(notes.map(n => n.id === note.id ? updatedNote : n));
      } catch (err) {
        console.error("Failed to update note:", err);
      }
    }
  };

  return (
    <div>
      <h2>My Notes</h2>

      {/* Add Note Form */}
      <form onSubmit={handleAddNote} style={{ marginBottom: "20px" }}>
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <textarea
          placeholder="Content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          required
        />
        <button type="submit">Add Note</button>
      </form>

      {/* Note List */}
      <NoteList notes={notes} onEdit={handleEdit} onDelete={handleDelete} />
    </div>
  );
};

export default NotesPage;
