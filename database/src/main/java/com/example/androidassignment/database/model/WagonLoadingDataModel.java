package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wagonLoading")
public class WagonLoadingDataModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    String rakeLoadingNumber;
    int wagonType;
    int wagonCapacity;
    String wagonSerialNum;
    boolean isSync;
    int tarpauline;
    int seal;
    int remarks;
    int doorLocked;
    int previousCargo;
    String wagonID;
    String spillBeans;
    String wagonNumber;
    public void setId(int id) {
        this.id = id;
    }

    String updatedAt;

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

    public String getWagonSerialNum() {
        return wagonSerialNum;
    }

    public void setWagonSerialNum(String wagonSerialNum) {
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

    public int getRemarks() {
        return remarks;
    }

    public int getPreviousCargo() {
        return previousCargo;
    }

    public void setPreviousCargo(int previousCargo) {
        this.previousCargo = previousCargo;
    }

    public void setRemarks(int remarks) {
        this.remarks = remarks;
    }

    public int getDoorLocked() {
        return doorLocked;
    }

    public void setDoorLocked(int doorLocked) {
        this.doorLocked = doorLocked;
    }

    public String getSpillBeans() {
        return spillBeans;
    }

    public void setSpillBeans(String spillBeans) {
        this.spillBeans = spillBeans;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public String getWagonID() {
        return wagonID;
    }

    public void setWagonID(String wagonID) {
        this.wagonID = wagonID;
    }

    public String getWagonNumber() {
        return wagonNumber;
    }

    public void setWagonNumber(String wagonNumber) {
        this.wagonNumber = wagonNumber;
    }
}


