package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.ItemCode;
import com.example.androidassignment.database.model.WareHouse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface WareHouseCodeDao {

    @Query("SELECT * FROM warehouse WHERE itemCode = :itemCode")
    Single<List<WareHouse>> getAllItemData(String itemCode);

    @Query("SELECT * FROM warehouse WHERE itemCode = :itemCode AND submitedTo = 0")
    List<WareHouse> getItemDataOnSubmited(String itemCode);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<WareHouse> model);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(WareHouse model);

    @Query("SELECT COUNT(value) FROM warehouse")
    int getCount();
}
