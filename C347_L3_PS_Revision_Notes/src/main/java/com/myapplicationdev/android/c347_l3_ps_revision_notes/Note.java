package com.myapplicationdev.android.c347_l3_ps_revision_notes;

import java.io.Serializable;

// Todo: POJO Object
public class Note implements Serializable {


    private int id;
    private String noteContent;
    private int stars;

    public Note(int id, String noteContent, int stars) {
        this.id = id;
        this.noteContent = noteContent;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public int getStars() {
        return stars;
    }
}

