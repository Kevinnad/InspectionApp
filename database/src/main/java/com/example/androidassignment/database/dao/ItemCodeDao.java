package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCode;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ItemCodeDao {

    @Query("SELECT * FROM itemcode WHERE orderNoId = :orderNo")
    Single<List<ItemCode>> getAllItemData(String orderNo);

    @Query("SELECT * FROM itemcode WHERE orderNoId = :orderNo AND submitedTo = 0")
    Single<List<ItemCode>> getItemDataOnSubmited(String orderNo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<ItemCode> model);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(ItemCode model);

    @Query("SELECT COUNT(value) FROM itemcode")
    int getCount();
}
