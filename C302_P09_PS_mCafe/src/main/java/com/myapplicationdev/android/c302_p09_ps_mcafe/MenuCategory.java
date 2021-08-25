package com.myapplicationdev.android.c302_p09_ps_mcafe;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MenuCategory implements Serializable {

    private String categoryId;
    private String description;

    public MenuCategory(String categoryId, String description) {
        this.categoryId = categoryId;
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
