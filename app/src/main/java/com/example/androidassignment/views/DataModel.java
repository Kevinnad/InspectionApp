package com.example.androidassignment.views;

public class DataModel {

    public DataModel(String name,
                     String min,
                     String max,
                     String actual) {

        this.actual = actual;
        this.min = min;
        this.max = max;
        this.name = name;

    }

    public String max;
    public String min;
    public String actual;
    public String name;
}