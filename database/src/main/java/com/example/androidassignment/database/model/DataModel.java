package com.example.androidassignment.database.model;

public class DataModel {

    public DataModel(String attribute,
                     String min,
                     String max,
                     String actual,String itemCode) {

        this.actual = actual;
        this.min = min;
        this.max = max;
        this.attribute = attribute;

    }

    public String max;
    public String min;
    public String actual;
    public String attribute;
    public String itemCode;
}