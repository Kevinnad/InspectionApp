package com.example.androidassignment.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.androidassignment.base.BaseRepository;
import com.example.androidassignment.dataStore.TruckUnloadingDataStore;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.WarehouseTruckUnloadingModel;
import com.example.network.service.Services;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WarehouseTruckUnloadingRepository extends BaseRepository {

    TruckUnloadingDataStore truckUnloadingDataStore;
    Services services;

    public WarehouseTruckUnloadingRepository(DataBaseProvider dataBaseProvider) {
        super(dataBaseProvider);
    }

    @Override
    public void initDataStore() {
        truckUnloadingDataStore = new TruckUnloadingDataStore();
    }

    @Override
    public void initNetwork() {
        services = Services.createService();
    }

    @Override
    public void getAllData(MutableLiveData mutableLiveData) {

    }

    @Override
    public void insertData(Object object, MutableLiveData mutableLiveData) {

        WarehouseTruckUnloadingModel inspectionDataModel = (WarehouseTruckUnloadingModel) object;

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                Long key = dataBaseProvider.getAppDatabase().warehouseTruckUnloadingDao().insert(inspectionDataModel);
                Log.e("Insert Success", "" + key);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (inspectionDataModel.isSync())
                            syncData(inspectionDataModel, mutableLiveData);
                        else
                            mutableLiveData.postValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

    }

    @Override
    public void getNext(Object object, MutableLiveData mutableLiveData) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().warehouseTruckUnloadingDao().getSingleInspection(currentId + 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<WarehouseTruckUnloadingModel>() {
            @Override
            public void accept(@NonNull WarehouseTruckUnloadingModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getPrevious(Object object, MutableLiveData mutableLiveData) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().warehouseTruckUnloadingDao().getSingleInspection(currentId - 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<WarehouseTruckUnloadingModel>() {
            @Override
            public void accept(@NonNull WarehouseTruckUnloadingModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getLastData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().warehouseTruckUnloadingDao().getLastInspection().subscribeOn(Schedulers.io()).subscribe(new Consumer<WarehouseTruckUnloadingModel>() {
            @Override
            public void accept(@NonNull WarehouseTruckUnloadingModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));

    }

    @Override
    public void syncData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().warehouseTruckUnloadingDao().getAll().subscribeOn(Schedulers.io()).subscribe(new Consumer<List<WarehouseTruckUnloadingModel>>() {
            @Override
            public void accept(List<WarehouseTruckUnloadingModel> inspectionDataModels) throws Throwable {

                for(WarehouseTruckUnloadingModel dataModel : inspectionDataModels){
                    dataModel.setSync(true);
                }

                dataBaseProvider.getAppDatabase().warehouseTruckUnloadingDao().insertAll(inspectionDataModels);
                mutableLiveData.postValue(inspectionDataModels);
            }
        });

//        InspectionAPIModel inspectionAPIModel = NetworkMapper.transfer((InspectionDataModel) object);
//
//        services.syncInspection(inspectionAPIModel)
//                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//                .subscribe(new Observer<Response>() {
//                    @Override
//                    public void onSubscribe(io.reactivex.disposables.Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Response value) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        mutableLiveData.postValue(true);
//                    }
//                });
    }
    public List<String> getWareHouseList(int i) {
        return truckUnloadingDataStore.getWareHouse(i);
    }

    public List<String> getStackList(int i) {
        return truckUnloadingDataStore.getStackList(i);
    }
}

