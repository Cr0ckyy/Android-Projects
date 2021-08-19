package com.myapplicationdev.android.c347_l1_ps_sgholidays;

public class Holiday {
    private String dayName;
    private String date;
    private int imageId;

    public Holiday(String dayName, String date, int imageId) {
        this.dayName = dayName;
        this.date = date;
        this.imageId = imageId;
    }

    public String getDayName() {
        return dayName;
    }

    public String getDate() {
        return date;
    }

    public int getImageId() {
        return imageId;
    }
}
