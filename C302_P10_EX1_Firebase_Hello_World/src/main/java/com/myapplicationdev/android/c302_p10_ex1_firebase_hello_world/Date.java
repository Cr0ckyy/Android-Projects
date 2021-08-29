package com.myapplicationdev.android.c302_p10_ex1_firebase_hello_world;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Date implements Serializable {

    private String title;
    private String date;

    public Date(String title, String date) {
        this.title = title;
        this.date = date;
    }

    // TODO: need an empty Constructor for for calls to snapshot.toObject(Date.class)
    public Date() {

    }

    @NonNull
    @Override
    public String toString() {
        return "Date{" +
                "text='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
