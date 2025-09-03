package com.pm.fsnotes.controller;

import com.pm.fsnotes.entity.Note;
import com.pm.fsnotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicNoteController {

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Fetch a note by its sharable link.
     * - No authentication required.
     * - Anyone with the link can view the note.
     *
     * @param link unique sharable link (UUID)
     * @return the note if found
     */
    @GetMapping("/{link}")
    public Note getNoteByLink(@PathVariable String link) {
        return noteRepository.findBySharablelink(link)
                .orElseThrow(() -> new RuntimeException("Note not found for given link"));
    }
}
