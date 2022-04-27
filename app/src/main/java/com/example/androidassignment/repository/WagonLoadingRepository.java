package com.example.androidassignment.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.androidassignment.base.BaseRepository;
import com.example.androidassignment.dataStore.InspectionDataStore;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;
import com.example.androidassignment.database.model.WagonLoadingDataModel;
import com.example.network.service.Services;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WagonLoadingRepository extends BaseRepository {

    InspectionDataStore inspectionDataStore;
    Services services;

    public WagonLoadingRepository(DataBaseProvider dataBaseProvider) {
        super(dataBaseProvider);
    }

    @Override
    public void initDataStore() {
        inspectionDataStore = new InspectionDataStore();
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

        WagonLoadingDataModel inspectionDataModel = (WagonLoadingDataModel) object;

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                Long key = dataBaseProvider.getAppDatabase().wagonLoadingDao().insert(inspectionDataModel);
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
    public void deleteData(Object object, MutableLiveData mutableLiveData) {

    }

    @Override
    public void getNext(Object object, MutableLiveData mutableLiveData, Object object2) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().wagonLoadingDao().getSingleInspection(currentId + 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<WagonLoadingDataModel>() {
            @Override
            public void accept(@NonNull WagonLoadingDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getPrevious(Object object, MutableLiveData mutableLiveData, Object object2) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().wagonLoadingDao().getSingleInspection(currentId - 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<WagonLoadingDataModel>() {
            @Override
            public void accept(@NonNull WagonLoadingDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getLastData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().wagonLoadingDao().getLastInspection().subscribeOn(Schedulers.io()).subscribe(new Consumer<WagonLoadingDataModel>() {
            @Override
            public void accept(@NonNull WagonLoadingDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));

    }

    @Override
    public void syncData(Object object, MutableLiveData mutableLiveData) {
//        mutableLiveData.postValue(true);

        dataBaseProvider.getAppDatabase().wagonLoadingDao().getAll().subscribeOn(Schedulers.io()).subscribe(new Consumer<List<WagonLoadingDataModel>>() {
            @Override
            public void accept(List<WagonLoadingDataModel> inspectionDataModels) throws Throwable {

                for(WagonLoadingDataModel dataModel : inspectionDataModels){
                    dataModel.setSync(true);
                }

                dataBaseProvider.getAppDatabase().wagonLoadingDao().insertAll(inspectionDataModels);
                mutableLiveData.postValue(inspectionDataModels);
            }
        });
    }


    public ItemCodeAttributesDataModel getInspectionItemList(int itemCode) {
        return inspectionDataStore.formInspectionItemList(itemCode);
    }

    public List<String> getWagonType(int i) {
        return inspectionDataStore.getWareHouse(i);
    }

    public List<String> getWagonCapacity(int i) {
        return inspectionDataStore.getWagonList(i);
    }

    public List<String> getWagonSerialNum(int i) {
        return inspectionDataStore.getItemCode("880000021");
    }
}

