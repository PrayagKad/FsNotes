package com.pm.fsnotes.dto;

public class CreatorDTO {
    private int id;
    private String username;

    public CreatorDTO(int id, String username) {
        this.id = id;
        this.username = username;
    }

    // getters
    public int getId() { return id; }
    public String getUsername() { return username; }
}
