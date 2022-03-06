package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.TruckUnloadingModel;
import com.example.androidassignment.database.model.WagonLoadingDataModel;
import com.example.androidassignment.database.model.WagonPreLoadingDataModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface WagonLoadingDao {
    
    @Query("SELECT * FROM wagonLoading")
    Single<List<WagonLoadingDataModel>> getAll();

    @Query("SELECT * FROM wagonLoading WHERE id = :id")
    Single<WagonLoadingDataModel> getSingleInspection(int id);


    @Query("SELECT * FROM wagonLoading ORDER BY id DESC LIMIT 1")
    Single<WagonLoadingDataModel> getLastInspection();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(WagonLoadingDataModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WagonLoadingDataModel> model);

    @Delete
    void delete(WagonLoadingDataModel model);


    @Update
    void update(WagonLoadingDataModel model);
}
