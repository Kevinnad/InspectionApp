package com.example.androidassignment.database.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidassignment.database.dao.InspectionDao;
import com.example.androidassignment.database.model.InspectionDataModel;

@Database(entities = {InspectionDataModel.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract InspectionDao inspectionDao();
}
