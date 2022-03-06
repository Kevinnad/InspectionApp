package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.TestInspectionModel;
import com.example.androidassignment.database.model.TruckLoadingDataModel;
import com.example.androidassignment.database.model.TruckUnloadingModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface TruckUnloadingDao {

    @Query("SELECT * FROM truck_unloading")
    Single<List<TruckUnloadingModel>> getAll();

    @Query("SELECT * FROM truck_unloading WHERE id = :id")
    Single<TruckUnloadingModel> getSingleInspection(int id);


    @Query("SELECT * FROM truck_unloading ORDER BY id DESC LIMIT 1")
    Single<TruckUnloadingModel> getLastInspection();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TruckUnloadingModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TruckUnloadingModel> model);

    @Delete
    void delete(TruckUnloadingModel model);

    @Update
    void update(TruckUnloadingModel model);
}
