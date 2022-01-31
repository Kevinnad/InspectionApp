package com.example.androidassignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.AppDataBase;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.views.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InspectionViewModel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> previousInspection = new MutableLiveData();
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
                        getPreviousData();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void getPreviousData() {

        if (currentId > 1) {
            dataBaseProvider.getAppDatabase().inspectionDao().getSingleInspection(currentId - 1).subscribe(new Consumer<InspectionDataModel>() {
                @Override
                public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                    handleResponse(inspectionDataModels);
                    currentId = inspectionDataModels.getId();
                }
            });
        }
    }

    public void getLastInspection() {

        dataBaseProvider.getAppDatabase().inspectionDao().getLastInspection().subscribe(new Consumer<InspectionDataModel>() {
            @Override
            public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                lastId = inspectionDataModels.getId();
                currentId = lastId+1;
            }
        });
    }

    private void handleResponse(InspectionDataModel inspectionDataModels) {
        previousInspection.postValue(inspectionDataModels);
    }

    void formInspectionItemList(){


        ArrayList<DataModel> list = new ArrayList<>();
        list.add(new DataModel("HL", "76", "82", ""));
        list.add(new DataModel("KB", "0", "0.1", ""));
        list.add(new DataModel("RDGRN", "0", "12", ""));
        list.add(new DataModel("POTIA", "0", "5", ""));

    }
}
