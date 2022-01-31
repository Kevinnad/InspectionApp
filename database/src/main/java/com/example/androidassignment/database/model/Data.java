package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Data {

    @PrimaryKey
    String name;
    String minValue;
    String maxValue;
    String actualValue;
}
