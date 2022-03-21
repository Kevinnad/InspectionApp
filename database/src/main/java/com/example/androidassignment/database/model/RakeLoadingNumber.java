package com.example.androidassignment.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RakeLoadingNumber {

    @PrimaryKey
    @NonNull
    public String value;
    public String inspectionCompleted;
    public boolean submitedTo;

    @Ignore
    public RakeLoadingNumber(String value) {
        this.value = value;
    }

    public RakeLoadingNumber(String value, String inspectionCompleted, boolean submitedTo) {
        this.value = value;
        this.inspectionCompleted = inspectionCompleted;
        this.submitedTo = submitedTo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInspectionCompleted() {
        return inspectionCompleted;
    }

    public void setInspectionCompleted(String inspectionCompleted) {
        this.inspectionCompleted = inspectionCompleted;
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
