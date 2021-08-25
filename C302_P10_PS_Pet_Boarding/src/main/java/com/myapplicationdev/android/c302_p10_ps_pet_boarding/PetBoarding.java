package com.myapplicationdev.android.c302_p10_ps_pet_boarding;

import java.io.Serializable;

public class PetBoarding implements Serializable {
    private String boardDate;
    private String name;
    private int numDays;
    private String petType;
    private boolean vaccinated;

    public PetBoarding(String boardDate, String name, int numDays, String petType, boolean vaccinated) {
        this.boardDate = boardDate;
        this.name = name;
        this.numDays = numDays;
        this.petType = petType;
        this.vaccinated = vaccinated;
    }

    // TODO: need an empty Constructor for for calls to snapshot.toObject(PetBoarding.class)
    public PetBoarding() {
    }

    public String getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(String boardDate) {
        this.boardDate = boardDate;
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

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
}
