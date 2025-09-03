package com.pm.fsnotes.entity;

import jakarta.persistence.*;

@Entity
public class note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    private String content;


    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    @Column(unique = true)
    private String sharablelink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public String getSharablelink() {
        return sharablelink;
    }

    public void setSharablelink(String sharablelink) {
        this.sharablelink = sharablelink;
    }
}
