package com.example.androidassignment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.TestInspectionModel;
import com.example.androidassignment.database.model.WagonLoadingDataModel;
import com.example.androidassignment.database.model.WagonPreLoadingDataModel;
import com.example.androidassignment.repository.WagonPreLoadingRepository;

import java.util.List;

public class WagonLoadingViewModel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<WagonLoadingDataModel> previousInspection = new MutableLiveData();
    public MutableLiveData<WagonLoadingDataModel> lastInspection = new MutableLiveData();
    public MutableLiveData<List<TestInspectionModel>> testInspection = new MutableLiveData();
    public int currentId = 0;
    public int lastId = 0;
    public WagonPreLoadingRepository inspectionRepository;

    public void initRepository() {
        inspectionRepository = new WagonPreLoadingRepository(dataBaseProvider);
    }

    public void addData(WagonLoadingDataModel dataModel) {

        inspectionRepository.insertData(dataModel, inserSuccessLiveData);
    }

    public void getPreviousData() {
        if (currentId > 1)
            inspectionRepository.getPrevious(currentId, previousInspection);
    }

    public void getNextData() {
        if (currentId <= lastId)
            inspectionRepository.getNext(currentId, previousInspection);
    }

    public void getLastInspection() {
        lastInspection.observeForever(new Observer<WagonLoadingDataModel>() {
            @Override
            public void onChanged(WagonLoadingDataModel dataModel) {
                lastId = dataModel.getId();
                currentId = lastId + 1;
            }
        });
        inspectionRepository.getLastData(currentId, lastInspection);
    }

}

