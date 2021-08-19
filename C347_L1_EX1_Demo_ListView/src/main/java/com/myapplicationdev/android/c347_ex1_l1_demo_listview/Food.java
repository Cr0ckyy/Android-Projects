package com.myapplicationdev.android.c347_ex1_l1_demo_listview;

public class Food {
    private final String name;
    private final boolean star;

    public Food(String name, boolean star) {
        this.star = star;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isStar() {
        return star;
    }
}
