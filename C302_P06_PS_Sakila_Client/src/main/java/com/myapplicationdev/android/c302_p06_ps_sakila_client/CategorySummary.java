package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import java.io.Serializable;

public class CategorySummary implements Serializable {
    private final String name;
    private final int count;

    public CategorySummary(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
