package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.TestInspectionModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface TestInspectionDao {

    @Query("SELECT * FROM testinspectionmodel")
    Single<List<TestInspectionModel>> getAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<TestInspectionModel> model);

    @Delete
    void delete(TestInspectionModel model);


    @Update
    void update(TestInspectionModel model);
}
