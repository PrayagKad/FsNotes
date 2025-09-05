import axios from "axios";
import { getToken } from "./authService";

const API_URL = `${process.env.REACT_APP_API_URL || "http://localhost:8080"}/notes`;

// Helper to set Authorization and JSON headers
const authHeader = () => ({
  headers: {
    Authorization: `Bearer ${getToken()}`,
    "Content-Type": "application/json"
  }
});

// Fetch all notes for logged-in user
export const getNotes = async () => {
  const response = await axios.get(API_URL, authHeader());
  return response.data;
};

// Create a new note
export const createNote = async (noteData) => {
  console.log("Sending note data to backend:", noteData);
  console.log("Auth header:", authHeader());
  const response = await axios.post(API_URL, noteData, authHeader());
  console.log("Backend response:", response.data);
  return response.data;
};

// Update a note
export const updateNote = async (id, noteData) => {
  const response = await axios.put(`${API_URL}/${id}`, noteData, authHeader());
  return response.data;
};

// Delete a note
export const deleteNote = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`, authHeader());
  return response.data;
};

// Get note by sharable link (public)
export const getNoteByLink = async (link) => {
  const response = await axios.get(`${API_URL}/share/${link}`);
  return response.data;
};
