package com.example.androidassignment.dataStore;

import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;

import java.util.ArrayList;
import java.util.List;

public class InspectionDataStore {

    public ItemCodeAttributesDataModel formInspectionItemList(int itemCode) {

        if (itemCode == 0) {
            ItemCodeAttributesDataModel itemCodeAttributesDataModel = new ItemCodeAttributesDataModel();

            ArrayList<Data> list1 = new ArrayList<>();
            list1.add(new Data("HL", "76", "82", ""));
            list1.add(new Data("KB", "0", "0.1", ""));
            list1.add(new Data("RDGRN", "0", "12", ""));
            list1.add(new Data("POTIA", "0", "5", ""));

            itemCodeAttributesDataModel.dataList = list1;
            return itemCodeAttributesDataModel;
        } else if (itemCode == 1) {

            ItemCodeAttributesDataModel itemCodeAttributesDataModel = new ItemCodeAttributesDataModel();
            ArrayList<Data> list3 = new ArrayList<>();
            list3.add(new Data("HL", "76", "82", ""));
            list3.add(new Data("RDGRN", "0", "12", ""));
            list3.add(new Data("POTIA", "0", "5", ""));

            itemCodeAttributesDataModel.dataList = list3;

            return itemCodeAttributesDataModel;
        } else {

            ItemCodeAttributesDataModel itemCodeAttributesDataModel = new ItemCodeAttributesDataModel();
            ArrayList<Data> list2 = new ArrayList<>();
            list2.add(new Data("HL", "76", "82", ""));
            list2.add(new Data("KB", "0", "0.1", ""));
            list2.add(new Data("RDGRN", "0", "12", ""));
            itemCodeAttributesDataModel.dataList = list2;
            return itemCodeAttributesDataModel;
        }

    }

    public List<String> getWareHouse(int i){
        ArrayList<String> list = new ArrayList<String>();
        if (i == 1) {
            list.add("MW01");
            list.add("MW02");
            list.add("MW03");
        }else {
            list.add("SW01");
            list.add("SW02");
            list.add("SW03");
        }
        return list;
    }

    public List<String> getStackList(int i){
        ArrayList<String> list = new ArrayList<String>();
        if (i == 1) {
            list.add("001MW01");
            list.add("001MW02");
            list.add("001MW02");
        }
        else if (i == 2) {
            list.add("002MW01");
            list.add("002MW02");
            list.add("002MW02");
        }else {
            list.add("003MW01");
            list.add("003MW02");
            list.add("003MW02");
        }
        return list;
    }

    public List<String> getItemCode(int i){
        ArrayList<String> list = new ArrayList<String>();
        if (i == 1) {
            list.add("F02002TJ60A");
            list.add("F02002TJ60B");
        }else {
            list.add("A02002TJ60A");
            list.add("A02002TJ60B");
        }
        return list;
    }


}
