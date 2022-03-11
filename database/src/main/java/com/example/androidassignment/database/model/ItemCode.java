package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemCode {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String value;
    public String orderNoId;
    public boolean submitedTo;

    public ItemCode(String value, String orderNoId) {
        this.value = value;
        this.orderNoId = orderNoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
