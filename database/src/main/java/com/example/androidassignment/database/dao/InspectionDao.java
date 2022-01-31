package com.example.androidassignment.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidassignment.database.model.InspectionDataModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface InspectionDao {

    @Query("SELECT * FROM inspectiontable")
    Single<List<InspectionDataModel>> getAll();

    @Query("SELECT * FROM inspectiontable WHERE id = :id")
    Single<InspectionDataModel> getSingleInspection(int id);

    @Query("SELECT * FROM inspectiontable ORDER BY id DESC LIMIT 1")
    Single<InspectionDataModel> getLastInspection();

    @Insert
    Long insert(InspectionDataModel model);

    @Delete
    void delete(InspectionDataModel model);

    @Update
    void update(InspectionDataModel model);
}
