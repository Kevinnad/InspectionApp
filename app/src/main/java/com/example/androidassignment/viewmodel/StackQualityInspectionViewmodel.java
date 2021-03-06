package com.example.androidassignment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.StackQualityInspectionModel;
import com.example.androidassignment.database.model.TestInspectionModel;
import com.example.androidassignment.repository.StackQualityInspectionRepository;

import java.util.List;

public class StackQualityInspectionViewmodel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<StackQualityInspectionModel> previousInspection = new MutableLiveData();
    public MutableLiveData<StackQualityInspectionModel> lastInspection = new MutableLiveData();
    public MutableLiveData<List<StackQualityInspectionModel>> syncInspection = new MutableLiveData();
    public int currentId = 0;
    public int lastId = 0;
    public StackQualityInspectionRepository inspectionRepository;

    public void initRepository() {
        inspectionRepository = new StackQualityInspectionRepository(dataBaseProvider);
    }

    public void addData(StackQualityInspectionModel dataModel) {

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
        lastInspection.observeForever(new Observer<StackQualityInspectionModel>() {
            @Override
            public void onChanged(StackQualityInspectionModel dataModel) {
                lastId = dataModel.getId();
                currentId = lastId + 1;
                if(dataModel.isSync()){
                    previousInspection.postValue(dataModel);
                }
            }
        });
        inspectionRepository.getLastData(currentId, lastInspection);
    }

    public void syncAllData(){
        inspectionRepository.syncData("",syncInspection);
    }

}

