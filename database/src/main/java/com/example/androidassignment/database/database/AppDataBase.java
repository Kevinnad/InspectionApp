package com.example.androidassignment.database.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.androidassignment.database.dao.InspectionDao;
import com.example.androidassignment.database.dao.ItemCodeDao;
import com.example.androidassignment.database.dao.RakeLoadingDao;
import com.example.androidassignment.database.dao.StackCodeDao;
import com.example.androidassignment.database.dao.StackQualityInspectionDao;
import com.example.androidassignment.database.dao.TruckUnloadingDao;
import com.example.androidassignment.database.dao.TruckLoadingDao;
import com.example.androidassignment.database.dao.WagonLoadingDao;
import com.example.androidassignment.database.dao.WagonPreLoadingDao;
import com.example.androidassignment.database.dao.WareHouseCodeDao;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCode;
import com.example.androidassignment.database.model.RakeLoadingNumber;
import com.example.androidassignment.database.model.StackModel;
import com.example.androidassignment.database.model.StackQualityInspectionModel;
import com.example.androidassignment.database.model.TestInspectionModel;
import com.example.androidassignment.database.model.TruckLoadingDataModel;
import com.example.androidassignment.database.model.TruckUnloadingModel;
import com.example.androidassignment.database.model.WagonLoadingDataModel;
import com.example.androidassignment.database.model.WagonPreLoadingDataModel;
import com.example.androidassignment.database.model.WareHouse;

@Database(entities = {InspectionDataModel.class, Data.class, TruckLoadingDataModel.class, TruckUnloadingModel.class,
        WagonLoadingDataModel.class, ItemCode.class, WareHouse.class, StackModel.class,
        WagonPreLoadingDataModel.class, StackQualityInspectionModel.class, RakeLoadingNumber.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class AppDataBase extends RoomDatabase {
    public abstract InspectionDao inspectionDao();

    public abstract ItemCodeDao itemCodeDao();

    public abstract WareHouseCodeDao wareHouseCodeDao();

    public abstract StackCodeDao stackCodeDao();

    public abstract TruckLoadingDao truckLoadingDao();

    public abstract TruckUnloadingDao truckUnloadingDao();

    public abstract WagonPreLoadingDao wagonPreLoadingDao();

    public abstract WagonLoadingDao wagonLoadingDao();

    public abstract StackQualityInspectionDao stackQualityInspectionDao();

    public abstract RakeLoadingDao rakeLoadingDao();
}
