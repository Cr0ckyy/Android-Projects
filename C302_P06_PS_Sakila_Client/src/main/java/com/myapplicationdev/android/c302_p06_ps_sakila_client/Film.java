package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import java.io.Serializable;

public class Film implements Serializable {

    private final String title;
    private final String description;
    private final int release_year;
    private final String rating;

    public Film(String title, String description, int release_year, String rating) {
        this.title = title;
        this.description = description;
        this.release_year = release_year;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRelease_year() {
        return release_year;
    }

    public String getRating() {
        return rating;
    }
}
