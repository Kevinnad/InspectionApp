package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "TruckLoadingTable")
public class TruckLoadingDataModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    String rakeLoadingNumber;
    String truckNumber;
    String numOfBags;
    String chelliCount;
    int quality;
    int orderNumber;
    int noOfTrucks;
    int wareHouse;
    int truckID;
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

    public int getNoOfTrucks() {
        return noOfTrucks;
    }

    public void setNoOfTrucks(int noOfTrucks) {
        this.noOfTrucks = noOfTrucks;
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

    public String getNumOfBags() {
        return numOfBags;
    }

    public void setNumOfBags(String numOfBags) {
        this.numOfBags = numOfBags;
    }

    public String getChelliCount() {
        return chelliCount;
    }

    public void setChelliCount(String chelliCount) {
        this.chelliCount = chelliCount;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getTruckID() {
        return truckID;
    }

    public void setTruckID(int truckID) {
        this.truckID = truckID;
    }
}
