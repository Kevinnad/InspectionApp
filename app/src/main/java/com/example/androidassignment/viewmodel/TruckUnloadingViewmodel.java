package com.example.androidassignment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.TruckUnloadingModel;
import com.example.androidassignment.repository.TruckUnloadingRepository;

import java.util.List;

public class TruckUnloadingViewmodel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<TruckUnloadingModel> lastInspection = new MutableLiveData();
    public MutableLiveData<TruckUnloadingModel> previousInspection = new MutableLiveData();
    public MutableLiveData<List<TruckUnloadingModel>> syncInspection = new MutableLiveData();
    public int currentId = 0;
    public int lastId = 0;
    public TruckUnloadingRepository inspectionRepository;


    public void initRepository() {
        inspectionRepository = new TruckUnloadingRepository(dataBaseProvider);
    }

    public void addData(TruckUnloadingModel dataModel) {

        inspectionRepository.insertData(dataModel, inserSuccessLiveData);
    }

    public void getPreviousData() {
        if (currentId > 1)
            inspectionRepository.getPrevious(currentId, previousInspection, "");
    }

    public void getNextData() {
        if (currentId <= lastId)
            inspectionRepository.getNext(currentId, previousInspection, "");
    }

    public void getLastInspection() {
        lastInspection.observeForever(new Observer<TruckUnloadingModel>() {
            @Override
            public void onChanged(TruckUnloadingModel dataModel) {
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

