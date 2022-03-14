package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.StackModel;
import com.example.androidassignment.database.model.WareHouse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface StackCodeDao {

    @Query("SELECT * FROM stackmodel WHERE warehouse = :wareHouse")
    Single<List<StackModel>> getAllItemData(String wareHouse);

    @Query("SELECT * FROM stackmodel WHERE warehouse = :wareHouse AND submitedTo = 0")
    List<StackModel> getItemDataOnSubmited(String wareHouse);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<StackModel> model);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(StackModel model);

    @Query("SELECT COUNT(value) FROM stackmodel")
    int getCount();
}
