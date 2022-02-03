package com.example.androidassignment.database.database;

import androidx.room.TypeConverter;

import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.DataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static ArrayList<Data> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Data>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Data> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
