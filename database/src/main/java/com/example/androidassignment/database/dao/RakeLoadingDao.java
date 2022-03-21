package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.ItemCode;
import com.example.androidassignment.database.model.RakeLoadingNumber;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface RakeLoadingDao {

    @Query("SELECT * FROM rakeloadingnumber")
    Single<List<RakeLoadingNumber>> getAllItemData();

    @Query("SELECT * FROM rakeloadingnumber WHERE inspectionCompleted = :inspectionCompleted")
    Single<List<RakeLoadingNumber>> getItemDataOnSubmited(String inspectionCompleted);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<RakeLoadingNumber> model);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(RakeLoadingNumber model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RakeLoadingNumber model);

    @Query("SELECT COUNT(value) FROM rakeloadingnumber")
    int getCount();
}
