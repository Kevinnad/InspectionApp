package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.StackQualityInspectionModel;
import com.example.androidassignment.database.model.TruckLoadingDataModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface TruckLoadingDao {
    @Query("SELECT * FROM truckloadingtable")
    Single<List<TruckLoadingDataModel>> getAll();

    @Query("SELECT * FROM truckloadingtable WHERE id = :id")
    Single<TruckLoadingDataModel> getSingleInspection(int id);


    @Query("SELECT * FROM truckloadingtable ORDER BY id DESC LIMIT 1")
    Single<TruckLoadingDataModel> getLastInspection();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TruckLoadingDataModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TruckLoadingDataModel> model);

    @Delete
    void delete(TruckLoadingDataModel model);

    @Update
    void update(TruckLoadingDataModel model);
}
