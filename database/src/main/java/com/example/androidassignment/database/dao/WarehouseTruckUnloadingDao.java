package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.WarehouseTruckUnloadingModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface WarehouseTruckUnloadingDao {

    @Query("SELECT * FROM wh_truck_unloading")
    Single<List<WarehouseTruckUnloadingModel>> getAll();

    @Query("SELECT * FROM wh_truck_unloading WHERE id = :id")
    Single<WarehouseTruckUnloadingModel> getSingleInspection(int id);


    @Query("SELECT * FROM wh_truck_unloading ORDER BY id DESC LIMIT 1")
    Single<WarehouseTruckUnloadingModel> getLastInspection();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(WarehouseTruckUnloadingModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WarehouseTruckUnloadingModel> model);

    @Delete
    void delete(WarehouseTruckUnloadingModel model);

    @Update
    void update(WarehouseTruckUnloadingModel model);
}
