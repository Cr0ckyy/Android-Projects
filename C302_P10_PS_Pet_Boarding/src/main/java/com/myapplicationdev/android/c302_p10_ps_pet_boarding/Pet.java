package com.myapplicationdev.android.c302_p10_ps_pet_boarding;


import java.io.Serializable;
import java.util.Date;

public class Pet implements Serializable {

    private String name;
    private int numDays;
    private String petType;
    private Date boardDate;
    private boolean vaccinated;

    // TODO: Default constructor required for calls to snapshot.toObject(Pet.class)
    public Pet() {
    }

    public Pet(String name, int numDays, String petType, Date boardDate, boolean vaccinated) {
        this.name = name;
        this.numDays = numDays;
        this.petType = petType;
        this.boardDate = boardDate;
        this.vaccinated = vaccinated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public Date getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(Date boardDate) {
        this.boardDate = boardDate;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
}