package com.example.androidassignment.base;

import androidx.lifecycle.MutableLiveData;

import com.example.androidassignment.dataStore.CommonDataStore;
import com.example.androidassignment.database.database.DataBaseProvider;

import java.util.List;

public abstract class BaseRepository {

    CommonDataStore commonDataStore;
    protected DataBaseProvider dataBaseProvider;

    public BaseRepository(DataBaseProvider dataBaseProvider) {
        initDataStore();
        initNetwork();
        this.dataBaseProvider = dataBaseProvider;
        commonDataStore = new CommonDataStore();
    }

    public abstract void initDataStore();

    public abstract void initNetwork();

    public abstract void getAllData(MutableLiveData mutableLiveData);

    public abstract void insertData(Object object, MutableLiveData mutableLiveData);

    public abstract void getNext(Object object, MutableLiveData mutableLiveData, Object object2);

    public abstract void getPrevious(Object object, MutableLiveData mutableLiveData, Object object2);

    public abstract void getLastData(Object object, MutableLiveData mutableLiveData);

    public abstract void syncData(Object object, MutableLiveData mutableLiveData);

    public List<String> getRackLoadingData(){
        return commonDataStore.getRackLoadingData();
    }

    public List<String> getOrderNumber(int item){
        return commonDataStore.getOrderNumber(item);
    }

}
