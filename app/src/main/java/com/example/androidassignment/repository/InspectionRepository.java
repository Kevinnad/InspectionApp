package com.example.androidassignment.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.androidassignment.base.BaseRepository;
import com.example.androidassignment.dataStore.InspectionDataStore;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCode;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;
import com.example.androidassignment.database.model.StackModel;
import com.example.androidassignment.database.model.WareHouse;
import com.example.network.service.Services;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InspectionRepository extends BaseRepository {

    InspectionDataStore inspectionDataStore;
    Services services;

    public InspectionRepository(DataBaseProvider dataBaseProvider) {
        super(dataBaseProvider);
    }

    @Override
    public void initDataStore() {
        inspectionDataStore = new InspectionDataStore();
    }

    @Override
    public void initNetwork() {
        services = Services.createService();
    }

    @Override
    public void getAllData(MutableLiveData mutableLiveData) {

    }

    @Override
    public void insertData(Object object, MutableLiveData mutableLiveData) {

        InspectionDataModel inspectionDataModel = (InspectionDataModel) object;

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                Long key = dataBaseProvider.getAppDatabase().inspectionDao().insert(inspectionDataModel);
                Log.e("Insert Success", "" + key);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mutableLiveData.postValue(true);
                        updateStack(inspectionDataModel, true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

    }

    @Override
    public void deleteData(Object object, MutableLiveData mutableLiveData) {

        InspectionDataModel inspectionDataModel = (InspectionDataModel) object;

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                dataBaseProvider.getAppDatabase().inspectionDao().delete(inspectionDataModel);
                Log.e("Delete Success", "" );
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mutableLiveData.postValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

    }

    @Override
    public void getNext(Object object, MutableLiveData mutableLiveData, Object object2) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().inspectionDao().getSingleInspection(currentId + 1, (String) object2).subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
            @Override
            public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getPrevious(Object object, MutableLiveData mutableLiveData, Object object2) {
        int currentId = (int) object;
        dataBaseProvider.getAppDatabase().inspectionDao().getSingleInspection(currentId - 1, (String) object2).subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
            @Override
            public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    @Override
    public void getLastData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().inspectionDao().getLastInspection((String) object).subscribeOn(Schedulers.io()).subscribe(new Consumer<InspectionDataModel>() {
            @Override
            public void accept(@NonNull InspectionDataModel inspectionDataModels) throws Exception {
                mutableLiveData.postValue(inspectionDataModels);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));

    }

    @Override
    public void syncData(Object object, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().inspectionDao().getAll().subscribeOn(Schedulers.io()).subscribe(new Consumer<List<InspectionDataModel>>() {
            @Override
            public void accept(List<InspectionDataModel> inspectionDataModels) throws Throwable {

                for (InspectionDataModel dataModel : inspectionDataModels) {
                    dataModel.setSync(true);
                }

                dataBaseProvider.getAppDatabase().inspectionDao().insertAll(inspectionDataModels);
                mutableLiveData.postValue(inspectionDataModels);
            }
        });


//        InspectionAPIModel inspectionAPIModel = NetworkMapper.transfer((InspectionDataModel) object);
//
//        services.syncInspection(inspectionAPIModel)
//                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//                .subscribe(new Observer<Response>() {
//                    @Override
//                    public void onSubscribe(io.reactivex.disposables.Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Response value) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        mutableLiveData.postValue(true);
//                    }
//                });
    }


    public ItemCodeAttributesDataModel getInspectionItemList(int itemCode) {
        return inspectionDataStore.formInspectionItemList(itemCode);
    }

    public void getWareHouseList(String itemCode, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().wareHouseCodeDao().getAllItemData(itemCode).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<WareHouse>>() {
            @Override
            public void accept(@NonNull List<WareHouse> data) throws Exception {
                mutableLiveData.postValue(data);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    public void getStackList(String wareHouse, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().stackCodeDao().getAllItemData(wareHouse).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<StackModel>>() {
            @Override
            public void accept(@NonNull List<StackModel> data) throws Exception {
                mutableLiveData.postValue(data);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    public void getItemCodes(String orderNo, MutableLiveData mutableLiveData) {

        dataBaseProvider.getAppDatabase().itemCodeDao().getAllItemData(orderNo).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<ItemCode>>() {
            @Override
            public void accept(@NonNull List<ItemCode> data) throws Exception {
                mutableLiveData.postValue(data);
            }
        }, throwable -> Log.e("", "Throwable " + throwable.getMessage()));
    }

    public void seedItemCode() {

        List<ItemCode> itemCodeList = inspectionDataStore.getItemCode();

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {

                int count = dataBaseProvider.getAppDatabase().itemCodeDao().getCount();
                if (count < 1) {
                    dataBaseProvider.getAppDatabase().itemCodeDao().insertAll(itemCodeList);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void seedWareHouse() {

        List<WareHouse> wareHouseList = inspectionDataStore.getWareHouse();

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {

                int count = dataBaseProvider.getAppDatabase().wareHouseCodeDao().getCount();
                if (count < 1) {
                    dataBaseProvider.getAppDatabase().wareHouseCodeDao().insertAll(wareHouseList);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void seedStack() {

        List<StackModel> stackList = inspectionDataStore.getStackList();

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {

                int count = dataBaseProvider.getAppDatabase().stackCodeDao().getCount();
                if (count < 1) {
                    dataBaseProvider.getAppDatabase().stackCodeDao().insertAll(stackList);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void updateItemCode(InspectionDataModel inspectionDataModel, boolean submited) {

        ItemCode itemCode1 = new ItemCode(inspectionDataModel.getItemCode(), inspectionDataModel.getOrderNumber(), submited);

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                dataBaseProvider.getAppDatabase().itemCodeDao().update(itemCode1);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void updateWareHouse(InspectionDataModel inspectionDataModel, boolean submited) {

        WareHouse wareHouse1 = new WareHouse(inspectionDataModel.getWareHouse(), inspectionDataModel.getItemCode(), submited);

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                dataBaseProvider.getAppDatabase().wareHouseCodeDao().update(wareHouse1);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        List<WareHouse> wareHouseList = dataBaseProvider.getAppDatabase().wareHouseCodeDao().getItemDataOnSubmited(inspectionDataModel.getItemCode());
                        if(wareHouseList == null || wareHouseList.size() < 1){
                            updateItemCode(inspectionDataModel,true);
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void updateStack(InspectionDataModel inspectionDataModel,boolean submited) {

        StackModel stackModel = new StackModel(inspectionDataModel.getStack(), inspectionDataModel.getWareHouse(), submited);

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                dataBaseProvider.getAppDatabase().stackCodeDao().update(stackModel);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        List<StackModel> stackModels = dataBaseProvider.getAppDatabase().stackCodeDao().getItemDataOnSubmited(inspectionDataModel.getWareHouse());
                        if(stackModels == null || stackModels.size() < 1){
                            updateWareHouse(inspectionDataModel,true);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}
