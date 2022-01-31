package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemCodeAttributesDataModel {
    @PrimaryKey
    String itemCode;
    String attributes;
    String min;
    String max;

}
