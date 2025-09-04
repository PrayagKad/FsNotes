package com.pm.fsnotes.controller;

import com.pm.fsnotes.dto.NoteDTO;
import com.pm.fsnotes.entity.Creator;
import com.pm.fsnotes.entity.Note;
import com.pm.fsnotes.repository.CreatorRepository;
import com.pm.fsnotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CreatorRepository creatorRepository;

    /**
     * Create a new note for the logged-in user.
     */
    @PostMapping
    public NoteDTO createNote(@RequestBody Note note, Authentication authentication) {
        String username = authentication.getName();

        Creator creator = creatorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        note.setCreator(creator);
        note.setSharablelink(UUID.randomUUID().toString());

        Note savedNote = noteRepository.save(note);
        return new NoteDTO(savedNote); // return safe DTO
    }

    /**
     * Get all notes for the logged-in user.
     */
    @GetMapping
    public List<NoteDTO> getAllNotes(Authentication authentication) {
        String username = authentication.getName();

        Creator creator = creatorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return noteRepository.findByCreator(creator)
                .stream()
                .map(NoteDTO::new) //  convert each Note -> NoteDTO
                .collect(Collectors.toList());
    }

    /**
     * Update a note by ID if it belongs to the logged-in user.
     */
    @PutMapping("/{id}")
    public NoteDTO updateNote(@PathVariable int id,
                              @RequestBody Note updatedNote,
                              Authentication authentication) {
        String username = authentication.getName();
        Creator creator = creatorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (note.getCreator().getId() != creator.getId()) {
            throw new RuntimeException("You cannot edit someone else's note");
        }

        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());

        Note savedNote = noteRepository.save(note);
        return new NoteDTO(savedNote); // safe response
    }

    /**
     * Delete a note by ID if it belongs to the logged-in user.
     */
    @DeleteMapping("/{id}")
    public Map<String, String> deleteNote(@PathVariable int id, Authentication authentication) {
        String username = authentication.getName();
        Creator creator = creatorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (note.getCreator().getId() != creator.getId()) {
            throw new RuntimeException("You cannot delete someone else's note");
        }

        noteRepository.delete(note);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Note deleted successfully");
        return response;
    }
    /**
     * Get a note by its sharable link (public access, no JWT required).
     *
     * Example: GET /notes/share/{link}
     */
    @GetMapping("/share/{link}")
    public Note getNoteByShareLink(@PathVariable String link) {
        return noteRepository.findBySharablelink(link)
                .orElseThrow(() -> new RuntimeException("Note not found or link invalid"));
    }

}
