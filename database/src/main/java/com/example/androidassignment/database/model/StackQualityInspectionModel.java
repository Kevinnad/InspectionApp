package com.example.androidassignment.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.androidassignment.database.database.Converters;

import java.util.ArrayList;

@Entity(tableName = "stack_quality")
public class StackQualityInspectionModel {

    @PrimaryKey(autoGenerate = true)
    int id;
    String rakeLoadingNumber;
    int orderNumber;
    int itemCode;
    int wareHouse;
    boolean isSync;
    @TypeConverters(Converters.class)
    ArrayList<Data> items;

    public void setId(int id) {
        this.id = id;
    }

    int stack;
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

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
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
}
