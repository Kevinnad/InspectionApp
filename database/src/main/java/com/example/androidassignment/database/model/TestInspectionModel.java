package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class TestInspectionModel {


    public TestInspectionModel(String name)
    {
        this.name = name;
    }
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    @Override
    public String toString() {
        return name;
    }
}
