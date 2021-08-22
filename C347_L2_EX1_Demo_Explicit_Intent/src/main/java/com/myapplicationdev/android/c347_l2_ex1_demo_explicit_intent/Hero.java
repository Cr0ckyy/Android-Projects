package com.myapplicationdev.android.c347_l2_ex1_demo_explicit_intent;


import java.io.Serializable;

public class Hero implements Serializable {
    private final String name;
    private final int strength;
    private final int technicalProwess;

    public Hero(String name, int strength, int technicalProwess) {
        this.name = name;
        this.strength = strength;
        this.technicalProwess = technicalProwess;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getTechnicalProwess() {
        return technicalProwess;
    }

}