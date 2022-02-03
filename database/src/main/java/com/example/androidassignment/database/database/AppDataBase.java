package com.example.androidassignment.database.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.androidassignment.database.dao.InspectionDao;
import com.example.androidassignment.database.dao.ItemCodeDao;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;

@Database(entities = {InspectionDataModel.class, Data.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDataBase extends RoomDatabase {
    public abstract InspectionDao inspectionDao();
    public abstract ItemCodeDao itemCodeDao();
}
