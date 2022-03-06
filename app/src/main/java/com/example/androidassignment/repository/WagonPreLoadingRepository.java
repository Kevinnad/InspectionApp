package com.example.androidassignment.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.androidassignment.base.BaseRepository;
import com.example.androidassignment.dataStore.InspectionDataStore;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.WagonLoadingDataModel;
import com.example.androidassignment.database.model.WagonPreLoadingDataModel;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;
import com.example.androidassignment.mapper.NetworkMapper;
import com.example.network.model.InspectionAPIModel;
import com.example.network.service.Services;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class WagonPreLoadingRepository extends BaseRepository {

    InspectionDataStore inspectionDataStore;
    Services services;

    public WagonPreLoadingRepository(DataBaseProvider dataBaseProvider) {
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

        WagonPreLoadingDataModel inspectionDataModel = (WagonPreLoadingDataModel) object;

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                Long key = dataBaseProvider.getAppDatabase().wagonPreLoadingDao().insert(inspectionDataModel);
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
        dataBaseProvider.getAppDatabase().wagonPreLoadingDao().getSingleInspection(currentId + 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<WagonPreLoadingDataModel>() {
            @Override
            public void accept(@NonNull WagonPreLoadingDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getPrevious(Object object, MutableLiveData mutableLiveData) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().wagonPreLoadingDao().getSingleInspection(currentId - 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<WagonPreLoadingDataModel>() {
            @Override
            public void accept(@NonNull WagonPreLoadingDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getLastData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().wagonPreLoadingDao().getLastInspection().subscribeOn(Schedulers.io()).subscribe(new Consumer<WagonPreLoadingDataModel>() {
            @Override
            public void accept(@NonNull WagonPreLoadingDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));

    }

    @Override
    public void syncData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().wagonPreLoadingDao().getAll().subscribeOn(Schedulers.io()).subscribe(new Consumer<List<WagonPreLoadingDataModel>>() {
            @Override
            public void accept(List<WagonPreLoadingDataModel> inspectionDataModels) throws Throwable {

                for(WagonPreLoadingDataModel dataModel : inspectionDataModels){
                    dataModel.setSync(true);
                }

                dataBaseProvider.getAppDatabase().wagonPreLoadingDao().insertAll(inspectionDataModels);
                mutableLiveData.postValue(inspectionDataModels);
            }
        });

//        InspectionAPIModel inspectionAPIModel = NetworkMapper.transfer((WagonPreLoadingDataModel) object);
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

    public List<String> getWagonType(int i) {
        return inspectionDataStore.getWareHouse(i);
    }

    public List<String> getWagonCapacity(int i) {
        return inspectionDataStore.getStackList(i);
    }

    public List<String> getWagonSerialNum(int i) {
        return inspectionDataStore.getItemCode(i);
    }
}

