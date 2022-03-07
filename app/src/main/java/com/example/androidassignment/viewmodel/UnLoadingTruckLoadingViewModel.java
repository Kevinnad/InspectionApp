package com.example.androidassignment.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.UnloadingTruckLoadingDataModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UnLoadingTruckLoadingViewModel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<UnloadingTruckLoadingDataModel> previousInspection = new MutableLiveData();
    public MutableLiveData<List<UnloadingTruckLoadingDataModel>> syncInspection = new MutableLiveData();
    public int currentId = 0;
    public int lastId = 0;


    public void addData(UnloadingTruckLoadingDataModel dataModel) {


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                Long key = dataBaseProvider.getAppDatabase().unLoadingTruckLoadingDao().insert(dataModel);
                currentId = key.intValue();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        inserSuccessLiveData.postValue(true);
                        getLastInspection();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void getPreviousData() {

        if (currentId > 1) {
            dataBaseProvider.getAppDatabase().unLoadingTruckLoadingDao().getSingleInspection(currentId - 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<UnloadingTruckLoadingDataModel>() {
                @Override
                public void accept(@NonNull UnloadingTruckLoadingDataModel inspectionDataModels) throws Exception {
                    handleResponse(inspectionDataModels);
                    currentId = inspectionDataModels.getId();
                }
            },throwable -> Log.e("", "Throwable " + throwable.getMessage()));

        }
    }
   public void getAllItemData(String itemCode) {

            dataBaseProvider.getAppDatabase().itemCodeDao().getAllItemData(itemCode).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Data>>() {
                @Override
                public void accept(@NonNull List<Data> data) throws Exception {

                }
            },throwable -> Log.e("", "Throwable " + throwable.getMessage()));


    }

    public void getNextData() {

        if (currentId <= lastId) {
            dataBaseProvider.getAppDatabase().unLoadingTruckLoadingDao().getSingleInspection(currentId + 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<UnloadingTruckLoadingDataModel>() {
                @Override
                public void accept(@NonNull UnloadingTruckLoadingDataModel inspectionDataModels) throws Exception {
                    handleResponse(inspectionDataModels);
                    currentId = inspectionDataModels.getId();
                }
            },throwable -> Log.e("", "Throwable " + throwable.getMessage()));

        }
    }

    public void getLastInspection() {

        dataBaseProvider.getAppDatabase().unLoadingTruckLoadingDao().getLastInspection().subscribeOn(Schedulers.io()).subscribe(new Consumer<UnloadingTruckLoadingDataModel>() {
            @Override
            public void accept(@NonNull UnloadingTruckLoadingDataModel inspectionDataModels) throws Exception {
                lastId = inspectionDataModels.getId();
                currentId = lastId + 1;
                if(inspectionDataModels.isSync()){
                    previousInspection.postValue(inspectionDataModels);
                }
            }
        },throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    private void handleResponse(UnloadingTruckLoadingDataModel inspectionDataModels) {
        previousInspection.postValue(inspectionDataModels);
    }

    public void syncAllData(){
        dataBaseProvider.getAppDatabase().unLoadingTruckLoadingDao().getAll().subscribeOn(Schedulers.io()).subscribe(new Consumer<List<UnloadingTruckLoadingDataModel>>() {
            @Override
            public void accept(List<UnloadingTruckLoadingDataModel> inspectionDataModels) throws Throwable {

                for(UnloadingTruckLoadingDataModel dataModel : inspectionDataModels){
                    dataModel.setSync(true);
                }

                dataBaseProvider.getAppDatabase().unLoadingTruckLoadingDao().insertAll(inspectionDataModels);
                syncInspection.postValue(inspectionDataModels);
            }
        });
    }

}
