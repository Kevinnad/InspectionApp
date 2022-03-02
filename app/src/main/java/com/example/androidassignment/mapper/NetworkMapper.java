package com.example.androidassignment.mapper;

import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.network.model.InspectionAPIModel;

public class NetworkMapper {

    public static InspectionAPIModel transfer(InspectionDataModel inspectionDataModel){
        InspectionAPIModel inspectionAPIModel = new InspectionAPIModel();
        inspectionAPIModel.setId(inspectionDataModel.getId());
        inspectionAPIModel.setItemCode(inspectionDataModel.getItemCode());
        inspectionAPIModel.setOrderNumber(inspectionDataModel.getOrderNumber());
        inspectionAPIModel.setRakeLoadingNumber(inspectionDataModel.getRakeLoadingNumber());
        inspectionAPIModel.setWareHouse(inspectionDataModel.getWareHouse());
        return inspectionAPIModel;
    }
}
