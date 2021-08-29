package com.myapplicationdev.android.c347_l11_ps_task_manager2;


import androidx.annotation.NonNull;

import java.io.Serializable;

class Task implements Serializable {
    private int id;
    private String name;
    private String desc;

    public Task(int id, String name, String desc) {
        this.id = id;
        this.desc = desc;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @NonNull
    @Override
    public String toString() {
        return id + " " + name + "\n" + desc;
    }
}