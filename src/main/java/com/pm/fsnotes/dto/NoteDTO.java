package com.pm.fsnotes.dto;

import com.pm.fsnotes.entity.Note;

public class NoteDTO {
    private int id;
    private String title;
    private String content;
    private com.pm.fsnotes.dto.CreatorDTO creator;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.creator = new com.pm.fsnotes.dto.CreatorDTO(
                note.getCreator().getId(),
                note.getCreator().getUsername()
        );
    }

    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public com.pm.fsnotes.dto.CreatorDTO getCreator() { return creator; }
}
