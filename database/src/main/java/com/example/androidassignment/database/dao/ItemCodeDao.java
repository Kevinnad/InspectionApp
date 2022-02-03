package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.androidassignment.database.model.Data;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ItemCodeDao {

    @Query("SELECT * FROM data WHERE itemCode = :itemCode")
    Single<List<Data>> getAllItemData(String itemCode);
}
