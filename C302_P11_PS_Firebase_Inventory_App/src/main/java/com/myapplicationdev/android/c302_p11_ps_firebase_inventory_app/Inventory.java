package com.myapplicationdev.android.c302_p11_ps_firebase_inventory_app;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Inventory implements Serializable {

    @Exclude private String id;
    private String itemName;
    private int unitCost;

    // TODO: need an empty Constructor for for calls to snapshot.toObject(Inventory.class)
    public Inventory(){
    }

    public Inventory(String id, String itemName, int unitCost) {
        this.id = id;
        this.itemName = itemName;
        this.unitCost = unitCost;
    }

    public Inventory(String itemName, int unitCost) {
        this.itemName = itemName;
        this.unitCost = unitCost;
    }


    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    @NonNull
    @Override
    public String toString() {
        return itemName;//only return name in this method as we are going to use this for ListView display purpose
    }

}
