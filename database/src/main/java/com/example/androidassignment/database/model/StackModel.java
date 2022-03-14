package com.example.androidassignment.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class StackModel {

    @PrimaryKey
    @NonNull
    public String value;
    public String warehouse;
    public boolean submitedTo;

    @Ignore
    public StackModel(String value, String warehouse) {
        this.value = value;
        this.warehouse = warehouse;
    }

    public StackModel(String value, String warehouse, boolean submitedTo) {
        this.value = value;
        this.warehouse = warehouse;
        this.submitedTo = submitedTo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
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
