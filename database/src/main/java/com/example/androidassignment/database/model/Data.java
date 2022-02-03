package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Data {

    @PrimaryKey
    public int id;
    String name;
    String minValue;
    String maxValue;
    String actualValue;
    String itemCode;

    public Data(String name, String minValue, String maxValue, String actualValue) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.actualValue = actualValue;
    }

    public String getName() {
        return name;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }
}
