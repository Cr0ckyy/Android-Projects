package com.myapplicationdev.android.c347_l3_ex1_demo_database;

public class Task {
    private int id;
    private String description, date;

    public Task(int id, String description, String date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}

