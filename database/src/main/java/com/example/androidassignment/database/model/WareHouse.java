package com.example.androidassignment.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class WareHouse {

    @PrimaryKey
    @NonNull
    public String value;
    public String itemCode;
    public boolean submitedTo;

    @Ignore
    public WareHouse(String value, String itemCode) {
        this.value = value;
        this.itemCode = itemCode;
    }

    public WareHouse(String value, String itemCode, boolean submitedTo) {
        this.value = value;
        this.itemCode = itemCode;
        this.submitedTo = submitedTo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public boolean isSubmitedTo() {
        return submitedTo;
    }

    public void setSubmitedTo(boolean submitedTo) {
        this.submitedTo = submitedTo;
    }

    public String toString(){
        return value;
    }
}
