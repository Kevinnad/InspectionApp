package com.example.androidassignment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCode;
import com.example.androidassignment.database.model.StackModel;
import com.example.androidassignment.database.model.WareHouse;
import com.example.androidassignment.repository.InspectionRepository;

import java.util.List;

public class InspectionViewModel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> previousInspection = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> lastInspection = new MutableLiveData();
    public MutableLiveData<List<InspectionDataModel>> syncInspection = new MutableLiveData();

    public MutableLiveData<List<ItemCode>> itemCodeLiveData = new MutableLiveData();
    public MutableLiveData<List<WareHouse>> wareHouseLiveData = new MutableLiveData();
    public MutableLiveData<List<StackModel>> stackLiveData = new MutableLiveData();
    public int currentId = 0;
    public int lastId = 0;
    public InspectionRepository inspectionRepository;

    public void initRepository() {
        inspectionRepository = new InspectionRepository(dataBaseProvider);
    }

    public void addData(InspectionDataModel dataModel) {

        inspectionRepository.insertData(dataModel, inserSuccessLiveData);
    }

    public void getPreviousData(String orderNo) {
        if (currentId > 1)
            inspectionRepository.getPrevious(currentId, previousInspection, orderNo);
    }

    public void getNextData(String orderNo) {
        if (currentId <= lastId)
            inspectionRepository.getNext(currentId, previousInspection,orderNo);
    }

    public void getLastInspection(String orderNo) {
        lastInspection.observeForever(new Observer<InspectionDataModel>() {
            @Override
            public void onChanged(InspectionDataModel dataModel) {
                lastId = dataModel.getId();
                currentId = lastId + 1;
                if(dataModel.isSync()){
                    previousInspection.postValue(dataModel);
                }
            }
        });
        inspectionRepository.getLastData(orderNo, lastInspection);
    }

    public void syncAllData(){
        inspectionRepository.syncData("",syncInspection);
    }

    public void getItemCode(String orderNo){
        inspectionRepository.getItemCodes(orderNo,itemCodeLiveData);
    }

    public void getWareHouse(String itemCode){
        inspectionRepository.getWareHouseList(itemCode,wareHouseLiveData);
    }

    public void getStackList(String wareHouse){
        inspectionRepository.getStackList(wareHouse,stackLiveData);
    }

}
