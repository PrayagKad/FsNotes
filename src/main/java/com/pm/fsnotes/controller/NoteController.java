
package com.pm.fsnotes.controller;

import com.pm.fsnotes.entity.Creator;
import com.pm.fsnotes.entity.Note;
import com.pm.fsnotes.repository.CreatorRepository;
import com.pm.fsnotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CreatorRepository creatorRepository;

    /**
     * Create a new note for the logged-in user.
     * Steps:
     *  1. Get the username from Authentication object.
     *  2. Find the Creator (user) in DB.
     *  3. Set the creator for the note and generate a unique shareable link.
     *  4. Save and return the note.
     *
     * @param note note data from request body
     * @param authentication contains the logged-in user's details
     * @return the saved note with shareable link
     */
    @PostMapping
    public Note createNote(@RequestBody Note note, Authentication authentication) {
        String username = authentication.getName(); // Extract logged-in user's username

        Creator creator = creatorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        note.setCreator(creator); // Assign note to logged-in user
        note.setSharablelink(UUID.randomUUID().toString()); // Generate unique share link

        return noteRepository.save(note); // Save to DB
    }

    /**
     * Get all notes for the logged-in user.
     * Steps:
     *  1. Fetch current username.
     *  2. Find the user in DB.
     *  3. Return all notes linked to this user.
     *
     * @param authentication logged-in user
     * @return list of notes owned by the user
     */
    @GetMapping
    public List<Note> getAllNotes(Authentication authentication) {
        String username = authentication.getName();
        Creator creator = creatorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return noteRepository.findByCreator(creator);
    }

    /**
     * Update a note by ID if it belongs to the logged-in user.
     * Steps:
     *  1. Validate that the note exists.
     *  2. Ensure the note belongs to the logged-in user.
     *  3. Update fields and save.
     *
     * @param id note ID
     * @param updatedNote updated note data
     * @param authentication logged-in user
     * @return updated note
     */
    @PutMapping("/{id}")
    public Note updateNote(@PathVariable int id, @RequestBody Note updatedNote, Authentication authentication) {
        String username = authentication.getName();
        Creator creator = creatorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Check ownership
        if (note.getCreator().getId() != creator.getId()) {
            throw new RuntimeException("You cannot edit someone else's note");
        }

        // Update fields
        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());

        return noteRepository.save(note);
    }

    /**
     * Delete a note by ID if it belongs to the logged-in user.
     * Steps:
     *  1. Validate that the note exists.
     *  2. Ensure the note belongs to the logged-in user.
     *  3. Delete the note.
     *
     * @param id note ID
     * @param authentication logged-in user
     * @return confirmation message
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
}
