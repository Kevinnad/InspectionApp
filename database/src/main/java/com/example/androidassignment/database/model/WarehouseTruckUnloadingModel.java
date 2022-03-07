package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wh_truck_unloading")
public class WarehouseTruckUnloadingModel {

    @PrimaryKey(autoGenerate = true)
    int id;
    String rakeLoadingNumber;
    String truckNumber;
    String unloadedBags;
    String wagonNum;
    int quality;
    int orderNumber;
    int wareHouse;
    boolean isSync;
    int stack;
    String updatedAt;

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getRakeLoadingNumber() {
        return rakeLoadingNumber;
    }

    public void setRakeLoadingNumber(String rakeLoadingNumber) {
        this.rakeLoadingNumber = rakeLoadingNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }


    public int getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(int wareHouse) {
        this.wareHouse = wareHouse;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getUnloadedBags() {
        return unloadedBags;
    }

    public void setUnloadedBags(String unloadedBags) {
        this.unloadedBags = unloadedBags;
    }

    public String getWagonNum() {
        return wagonNum;
    }

    public void setWagonNum(String wagonNum) {
        this.wagonNum = wagonNum;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
