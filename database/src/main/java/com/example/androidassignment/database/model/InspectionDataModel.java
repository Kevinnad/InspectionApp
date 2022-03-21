package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.androidassignment.database.database.Converters;

import java.util.ArrayList;


@Entity(tableName = "InspectionTable")
public class InspectionDataModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    String rakeLoadingNumber;
    String orderNumber;
    String itemCode;
    String wareHouse;
    boolean isSync;
    @TypeConverters(Converters.class)
    ArrayList<Data> items;

    public void setId(int id) {
        this.id = id;
    }

    String stack;
    String updatedAt;
    int count;

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public ArrayList<Data> getItems() {
        return items;
    }

    public void setItems(ArrayList<Data> items) {
        this.items = items;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
