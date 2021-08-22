package com.myapplicationdev.android.c347_l4_ps_ndp_songs;

import java.io.Serializable;

public class Song implements Serializable {
    private final int _id;
    private final String title;
    private final String singers;
    private final int year;
    private final int stars;

    public Song(int _id, String title, String singers, int year, int stars) {
        this._id = _id;
        this.title = title;
        this.singers = singers;
        this.year = year;
        this.stars = stars;
    }

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getSingers() {
        return singers;
    }

    public int getYear() {
        return year;
    }

    public int getStars() {
        return stars;
    }
}
