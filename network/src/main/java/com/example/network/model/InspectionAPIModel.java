package com.example.network.model;

public class InspectionAPIModel {

    int id;
    String rakeLoadingNumber;
    String orderNumber;
    int itemCode;
    int wareHouse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
