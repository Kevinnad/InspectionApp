package com.example.androidassignment.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCode;
import com.example.androidassignment.database.model.RakeLoadingNumber;
import com.example.androidassignment.database.model.StackModel;
import com.example.androidassignment.database.model.WareHouse;
import com.example.androidassignment.repository.InspectionRepository;

import java.util.ArrayList;
import java.util.List;

public class InspectionViewModel extends ViewModel {

    public DataBaseProvider dataBaseProvider;
    public MutableLiveData<Boolean> inserSuccessLiveData = new MutableLiveData();
    public MutableLiveData<Boolean> deleteSuccessLiveData = new MutableLiveData();

    public MutableLiveData<InspectionDataModel> inspectionList = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> previousInspection = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> lastInspection = new MutableLiveData();
    public MutableLiveData<InspectionDataModel> lastInspectionCompleted = new MutableLiveData();
    public MutableLiveData<List<InspectionDataModel>> syncInspection = new MutableLiveData();

    public MutableLiveData<List<ItemCode>> itemCodeLiveData = new MutableLiveData();
    public MutableLiveData<List<WareHouse>> wareHouseLiveData = new MutableLiveData();
    public MutableLiveData<List<StackModel>> stackLiveData = new MutableLiveData();
    public MutableLiveData<List<RakeLoadingNumber>> rakeLoadingLiveData = new MutableLiveData();
    public int currentId = 0;
    public int lastId = 0;
    public List<InspectionDataModel> list = new ArrayList<>();
    public InspectionRepository inspectionRepository;

    public void initRepository() {
        inspectionRepository = new InspectionRepository(dataBaseProvider);
    }

    public void addData(InspectionDataModel dataModel) {

        inspectionRepository.insertData(dataModel, inserSuccessLiveData);
    }

    public void deleteData(InspectionDataModel dataModel){
        inspectionRepository.deleteData(dataModel, deleteSuccessLiveData);
    }


    public void deleteSingleInspection(InspectionDataModel dataModel) {

        inspectionRepository.insertData(dataModel, inserSuccessLiveData);
    }

    public void getPreviousData(String orderNo) {
        if (currentId > 0) {
            currentId = currentId - 1;
            previousInspection.postValue(list.get(currentId));
        }
    }

    public void getNextData(String orderNo) {
        if (currentId <= lastId) {
            currentId = currentId + 1;
            previousInspection.postValue(list.get(currentId));
        }
    }

    public void getLastInspection(String orderNo) {
//        Observer observer = new Observer<InspectionDataModel>() {
//
//            @Override
//            public void onChanged(InspectionDataModel dataModel) {
//                if(dataModel != null){
//
//                    lastId = dataModel.getId();
//                    currentId = lastId + 1;
//                    if(dataModel.isSync()){
//                        previousInspection.postValue(dataModel);
//                    }else{
//                        lastInspectionCompleted.postValue(dataModel);
//                    }
//                }else {
//                    lastInspectionCompleted.postValue(dataModel);
//                }
//
//            }
//        };
 Observer observer1 = new Observer<List<InspectionDataModel>>() {

            @Override
            public void onChanged(List<InspectionDataModel> dataModel) {
                if(!dataModel.isEmpty()){
                    list = dataModel;
                    lastId = dataModel.size()-1;
                    currentId = lastId + 1;
                    if(dataModel.get(lastId).isSync()){
                        previousInspection.postValue(dataModel.get(lastId));
                    }else{
                        lastInspectionCompleted.postValue(dataModel.get(lastId));
                    }

                }else {
                    lastInspectionCompleted.postValue(null);
                }

            }
        };

//        lastInspection.removeObserver(observer);
//        lastInspection.observeForever(observer);
        inspectionList.removeObserver(observer1);
        inspectionList.observeForever(observer1
        );
        inspectionRepository.getLastData(orderNo, lastInspection);
        inspectionRepository.getDataList(orderNo, inspectionList);
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

    public void getRakeLoadingNumber(){
        inspectionRepository.getRakeLoadingFromDB(rakeLoadingLiveData);
    }

    public void addRakeLoadingNumber(int i){
        inspectionRepository.insertRakeLoading(rakeLoadingLiveData,i);
    }

}
