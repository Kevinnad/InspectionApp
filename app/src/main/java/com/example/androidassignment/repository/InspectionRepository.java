package com.example.androidassignment.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.androidassignment.base.BaseRepository;
import com.example.androidassignment.dataStore.InspectionDataStore;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;
import com.example.network.service.Services;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InspectionRepository extends BaseRepository {

    InspectionDataStore inspectionDataStore;
    Services services;

    public InspectionRepository(DataBaseProvider dataBaseProvider) {
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

        InspectionDataModel inspectionDataModel = (InspectionDataModel) object;

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                Long key = dataBaseProvider.getAppDatabase().inspectionDao().insert(inspectionDataModel);
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
                            mutableLiveData.postValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

    }

    @Override
    public void getNext(Object object, MutableLiveData mutableLiveData, Object object2) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().inspectionDao().getSingleInspection(currentId + 1,(String) object2).subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
            @Override
            public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getPrevious(Object object, MutableLiveData mutableLiveData, Object object2) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().inspectionDao().getSingleInspection(currentId - 1,(String) object2).subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
            @Override
            public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getLastData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().inspectionDao().getLastInspection((String) object).subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
            @Override
            public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));

    }

    @Override
    public void syncData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().inspectionDao().getAll().subscribeOn(Schedulers.io()).subscribe(new Consumer<List<InspectionDataModel>>() {
            @Override
            public void accept(List<InspectionDataModel> inspectionDataModels) throws Throwable {

                for(InspectionDataModel dataModel : inspectionDataModels){
                    dataModel.setSync(true);
                }

                dataBaseProvider.getAppDatabase().inspectionDao().insertAll(inspectionDataModels);
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



    public ItemCodeAttributesDataModel getInspectionItemList(int itemCode) {
        return inspectionDataStore.formInspectionItemList(itemCode);
    }

    public List<String> getWareHouseList(int i) {
        return inspectionDataStore.getWareHouse(i);
    }

    public List<String> getStackList(int i) {
        return inspectionDataStore.getStackList(i);
    }

    public List<String> getItemCode(String i) {
        return inspectionDataStore.getItemCode(i);
    }
}
