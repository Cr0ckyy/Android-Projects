package com.myapplicationdev.android.c302_php_mysql_magic_assignment;

import java.io.Serializable;

public class Colour implements Serializable {

    private int colourId;
    private String colourName;

    public Colour(int colourId, String colourName) {
        this.colourId = colourId;
        this.colourName = colourName;
    }

    public int getColourId() {
        return colourId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }
}