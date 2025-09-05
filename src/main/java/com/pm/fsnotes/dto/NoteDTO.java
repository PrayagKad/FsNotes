package com.pm.fsnotes.dto;

import com.pm.fsnotes.entity.Note;
import java.time.LocalDateTime;

public class NoteDTO {
    private int id;
    private String title;
    private String content;
    private String sharablelink;
    private LocalDateTime createdAt;
    private com.pm.fsnotes.dto.CreatorDTO creator;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.sharablelink = note.getSharablelink();
        this.createdAt = note.getCreatedAt();
        this.creator = new com.pm.fsnotes.dto.CreatorDTO(
                note.getCreator().getId(),
                note.getCreator().getUsername()
        );
    }

    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getSharablelink() { return sharablelink; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public com.pm.fsnotes.dto.CreatorDTO getCreator() { return creator; }
}
