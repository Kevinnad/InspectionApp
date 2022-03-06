package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.StackQualityInspectionModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface StackQualityInspectionDao {
    @Query("SELECT * FROM stack_quality")
    Single<List<StackQualityInspectionModel>> getAll();

    @Query("SELECT * FROM stack_quality WHERE id = :id")
    Single<StackQualityInspectionModel> getSingleInspection(int id);


    @Query("SELECT * FROM stack_quality ORDER BY id DESC LIMIT 1")
    Single<StackQualityInspectionModel> getLastInspection();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(StackQualityInspectionModel model);

    @Delete
    void delete(StackQualityInspectionModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StackQualityInspectionModel> model);

    @Update
    void update(StackQualityInspectionModel model);
}
