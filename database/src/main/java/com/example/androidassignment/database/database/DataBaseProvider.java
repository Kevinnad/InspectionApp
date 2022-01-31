package com.example.androidassignment.database.database;

import android.content.Context;

import androidx.room.Room;

public class DataBaseProvider {

    private static DataBaseProvider mInstance;
    private AppDataBase appDatabase;

    private DataBaseProvider(Context context) {
        appDatabase = Room.databaseBuilder(context,
                AppDataBase.class, "inspection-database").build();

    }


    public static synchronized DataBaseProvider getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DataBaseProvider(mCtx);
        }
        return mInstance;
    }

    public AppDataBase getAppDatabase() {
        return appDatabase;
    }


}
