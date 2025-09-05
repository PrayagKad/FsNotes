import React, { useState, useEffect, useContext } from "react";
import Navbar from "../components/NavBar";
import NoteForm from "../components/NoteForm";
import NoteList from "../components/NoteList";
import { AuthContext } from "../context/AuthContext";
import { getNotes, createNote, updateNote, deleteNote as deleteNoteApi } from "../api/noteService";

const Homepage = () => {
  const { user } = useContext(AuthContext);
  const [notes, setNotes] = useState([]);
  const [editingNote, setEditingNote] = useState(null);

  const fetchNotes = async () => {
    try {
      const res = await getNotes();
      setNotes(res);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (user) fetchNotes();
  }, [user]);

  const addOrUpdateNote = async (noteData) => {
    try {
      console.log("Adding/updating note:", noteData);
      if (editingNote) {
        console.log("Updating note with ID:", editingNote.id);
        await updateNote(editingNote.id, noteData);
        setEditingNote(null);
      } else {
        console.log("Creating new note");
        const result = await createNote(noteData);
        console.log("Note created successfully:", result);
      }
      await fetchNotes();
    } catch (err) {
      console.error("Error adding/updating note:", err);
      alert("Error saving note: " + (err.response?.data?.message || err.message));
    }
  };

  const deleteNote = async (id) => {
    try {
      await deleteNoteApi(id);
      fetchNotes();
    } catch (err) {
      console.error(err);
    }
  };

  const editNote = (note) => setEditingNote(note);

  return (
    <div>
      <Navbar />
      <div className="container" style={{ padding: "40px 20px" }}>
        <div style={{ maxWidth: "800px", margin: "0 auto" }}>
          <NoteForm onSave={addOrUpdateNote} initialData={editingNote} />
          <NoteList notes={notes} onEdit={editNote} onDelete={deleteNote} />
        </div>
      </div>
    </div>
  );
};

export default Homepage;
