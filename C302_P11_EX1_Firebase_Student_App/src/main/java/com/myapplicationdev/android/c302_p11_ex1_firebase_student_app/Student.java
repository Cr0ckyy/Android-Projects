package com.myapplicationdev.android.c302_p11_ex1_firebase_student_app;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Student implements Serializable {

    private String id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return name;//only return name in this method as we are going to use this for ListView display purpose
    }

}
