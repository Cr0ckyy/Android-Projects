package com.myapplicationdev.android.c347_l2_ps_class_journal;

public class Module {

    //---------------------Initial Setting--------------
    private int moduleWeek;
    private String moduleGrade;

    public Module(String c347, int moduleWeek, String moduleGrade) {

        this.moduleWeek = moduleWeek;
        this.moduleGrade = moduleGrade;
    }
    //---------------------Initial Setting--------------


    //---------------------Getter--------------
    public int getModuleWeek() {
        return moduleWeek;
    }

    //---------------------Setter--------------
    public void setModuleWeek(int moduleWeek) {
        this.moduleWeek = moduleWeek;
    }
    //---------------------Getter--------------

    public String getModuleGrade() {
        return moduleGrade;
    }

    public void setModuleGrade(String moduleGrade) {
        this.moduleGrade = moduleGrade;
    }
    //---------------------Setter--------------
}

