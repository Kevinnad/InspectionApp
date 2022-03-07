package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.androidassignment.database.database.Converters;

import java.util.ArrayList;

@Entity(tableName = "wagonPreLoading")
public class WagonPreLoadingDataModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    String rakeLoadingNumber;
    int wagonType;
    int wagonCapacity;
    int wagonSerialNum;
    boolean isSync;
    int tarpauline;
    int seal;
    String remarks;
    String  doorLocked;
    String truckNo;
    String spillBeans;
    String sealNum;
    String totalWeight;
    public void setId(int id) {
        this.id = id;
    }

    String updatedAt;

    public String getSealNum() {
        return sealNum;
    }

    public void setSealNum(String sealNum) {
        this.sealNum = sealNum;
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

    public int getWagonType() {
        return wagonType;
    }

    public void setWagonType(int wagonType) {
        this.wagonType = wagonType;
    }

    public int getWagonCapacity() {
        return wagonCapacity;
    }

    public void setWagonCapacity(int wagonCapacity) {
        this.wagonCapacity = wagonCapacity;
    }

    public int getWagonSerialNum() {
        return wagonSerialNum;
    }

    public void setWagonSerialNum(int wagonSerialNum) {
        this.wagonSerialNum = wagonSerialNum;
    }

    public int getTarpauline() {
        return tarpauline;
    }

    public void setTarpauline(int tarpauline) {
        this.tarpauline = tarpauline;
    }

    public int getSeal() {
        return seal;
    }

    public void setSeal(int seal) {
        this.seal = seal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDoorLocked() {
        return doorLocked;
    }

    public void setDoorLocked(String  doorLocked) {
        this.doorLocked = doorLocked;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String getSpillBeans() {
        return spillBeans;
    }

    public void setSpillBeans(String spillBeans) {
        this.spillBeans = spillBeans;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }
}


