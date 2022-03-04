package com.example.androidassignment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.TestInspectionModel;
import com.example.androidassignment.repository.InspectionRepository;

import java.util.List;

public class InspectionViewModel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> previousInspection = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> lastInspection = new MutableLiveData();
    public MutableLiveData<List<TestInspectionModel>> testInspection = new MutableLiveData();
    public int currentId = 0;
    public int lastId = 0;
    public InspectionRepository inspectionRepository;

    public void initRepository() {
        inspectionRepository = new InspectionRepository(dataBaseProvider);
    }

    public void addData(InspectionDataModel dataModel) {

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
        lastInspection.observeForever(new Observer<InspectionDataModel>() {
            @Override
            public void onChanged(InspectionDataModel dataModel) {
                lastId = dataModel.getId();
                currentId = lastId + 1;
            }
        });
        inspectionRepository.getLastData(currentId, lastInspection);
    }

}
