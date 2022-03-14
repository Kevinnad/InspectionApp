package com.example.androidassignment.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ItemCode {

    @PrimaryKey
    @NonNull
    public String value;
    public String orderNoId;
    public boolean submitedTo;

    @Ignore
    public ItemCode(String value, String orderNoId) {
        this.value = value;
        this.orderNoId = orderNoId;
    }

    public ItemCode(String value, String orderNoId, boolean submitedTo) {
        this.value = value;
        this.orderNoId = orderNoId;
        this.submitedTo = submitedTo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOrderNoId() {
        return orderNoId;
    }

    public void setOrderNoId(String orderNoId) {
        this.orderNoId = orderNoId;
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
