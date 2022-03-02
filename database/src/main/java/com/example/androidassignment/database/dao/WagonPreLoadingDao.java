package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.WagonPreLoadingDataModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface WagonPreLoadingDao {
    
    @Query("SELECT * FROM wagonPreLoading")
    Single<List<WagonPreLoadingDataModel>> getAll();

    @Query("SELECT * FROM wagonPreLoading WHERE id = :id")
    Single<WagonPreLoadingDataModel> getSingleInspection(int id);


    @Query("SELECT * FROM wagonPreLoading ORDER BY id DESC LIMIT 1")
    Single<WagonPreLoadingDataModel> getLastInspection();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(WagonPreLoadingDataModel model);

    @Delete
    void delete(WagonPreLoadingDataModel model);


    @Update
    void update(WagonPreLoadingDataModel model);
}
