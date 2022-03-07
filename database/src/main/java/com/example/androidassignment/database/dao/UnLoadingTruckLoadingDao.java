package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.UnloadingTruckLoadingDataModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface UnLoadingTruckLoadingDao {
    @Query("SELECT * FROM unloadingtruckloadingdatamodel")
    Single<List<UnloadingTruckLoadingDataModel>> getAll();

    @Query("SELECT * FROM unloadingtruckloadingdatamodel WHERE id = :id")
    Single<UnloadingTruckLoadingDataModel> getSingleInspection(int id);


    @Query("SELECT * FROM unloadingtruckloadingdatamodel ORDER BY id DESC LIMIT 1")
    Single<UnloadingTruckLoadingDataModel> getLastInspection();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(UnloadingTruckLoadingDataModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UnloadingTruckLoadingDataModel> model);

    @Delete
    void delete(UnloadingTruckLoadingDataModel model);

    @Update
    void update(UnloadingTruckLoadingDataModel model);
}
