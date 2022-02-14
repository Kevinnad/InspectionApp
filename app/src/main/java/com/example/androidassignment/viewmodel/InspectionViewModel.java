package com.example.androidassignment.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;
import com.example.androidassignment.database.model.TestInspectionModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InspectionViewModel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> previousInspection = new MutableLiveData();
    public MutableLiveData<List<TestInspectionModel>> testInspection = new MutableLiveData();
    public int currentId = 0;
    public int lastId = 0;


    public void addData(InspectionDataModel dataModel) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                Long key = dataBaseProvider.getAppDatabase().inspectionDao().insert(dataModel);
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
            dataBaseProvider.getAppDatabase().inspectionDao().getSingleInspection(currentId - 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
                @Override
                public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
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


    }public void getAllData() {

            dataBaseProvider.getAppDatabase().testInspectionDao().getAll().subscribeOn(Schedulers.io()).subscribe(new Consumer<List<TestInspectionModel>>() {
                @Override
                public void accept(@NonNull List<TestInspectionModel> data) throws Exception {
                    testInspection.postValue(data);
                }
            },throwable -> Log.e("", "Throwable " + throwable.getMessage()));


    }

    public void insertAllTestInspectionData(List<TestInspectionModel> models) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                dataBaseProvider.getAppDatabase().testInspectionDao().insert(models);

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

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("test",e.getMessage());

                    }
                });

    }

    public void getNextData() {

        if (currentId <= lastId) {
            dataBaseProvider.getAppDatabase().inspectionDao().getSingleInspection(currentId + 1).subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
                @Override
                public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                    handleResponse(inspectionDataModels);
                    currentId = inspectionDataModels.getId();
                }
            },throwable -> Log.e("", "Throwable " + throwable.getMessage()));

        }
    }

    public void getLastInspection() {

        dataBaseProvider.getAppDatabase().inspectionDao().getLastInspection().subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
            @Override
            public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                lastId = inspectionDataModels.getId();
                currentId = lastId + 1;
            }
        },throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    private void handleResponse(InspectionDataModel inspectionDataModels) {
        previousInspection.postValue(inspectionDataModels);
    }

    public ItemCodeAttributesDataModel formInspectionItemList(int itemCode) {

        if (itemCode == 0) {
            ItemCodeAttributesDataModel itemCodeAttributesDataModel = new ItemCodeAttributesDataModel();

            ArrayList<Data> list1 = new ArrayList<>();
            list1.add(new Data("HL", "76", "82", ""));
            list1.add(new Data("KB", "0", "0.1", ""));
            list1.add(new Data("RDGRN", "0", "12", ""));
            list1.add(new Data("POTIA", "0", "5", ""));

            itemCodeAttributesDataModel.dataList = list1;
            return itemCodeAttributesDataModel;
        } else if (itemCode == 1) {

            ItemCodeAttributesDataModel itemCodeAttributesDataModel = new ItemCodeAttributesDataModel();
            ArrayList<Data> list3 = new ArrayList<>();
            list3.add(new Data("HL", "76", "82", ""));
            list3.add(new Data("RDGRN", "0", "12", ""));
            list3.add(new Data("POTIA", "0", "5", ""));

            itemCodeAttributesDataModel.dataList = list3;

            return itemCodeAttributesDataModel;
        } else {

            ItemCodeAttributesDataModel itemCodeAttributesDataModel = new ItemCodeAttributesDataModel();
            ArrayList<Data> list2 = new ArrayList<>();
            list2.add(new Data("HL", "76", "82", ""));
            list2.add(new Data("KB", "0", "0.1", ""));
            list2.add(new Data("RDGRN", "0", "12", ""));
            itemCodeAttributesDataModel.dataList = list2;
            return itemCodeAttributesDataModel;
        }

    }
}
