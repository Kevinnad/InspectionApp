package com.example.androidassignment.dataStore;

import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.ItemCode;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;
import com.example.androidassignment.database.model.StackModel;
import com.example.androidassignment.database.model.WareHouse;

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
            list2.add(new Data("HL", "12", "32", ""));
            list2.add(new Data("KB", "0", "5", ""));
            list2.add(new Data("RDGRN", "2", "18", ""));
            itemCodeAttributesDataModel.dataList = list2;
            return itemCodeAttributesDataModel;
        }

    }

    public List<String> getWareHouse(int i) {
        ArrayList<String> list = new ArrayList<String>();
        if (i == 1) {
            list.add("MW01");
            list.add("MW02");
            list.add("MW03");
        } else {
            list.add("SW01");
            list.add("SW02");
            list.add("SW03");
        }
        return list;
    }

    public List<String> getStackList(int i) {
        ArrayList<String> list = new ArrayList<String>();
        if (i == 1) {
            list.add("001MW01");
            list.add("001MW02");
            list.add("001MW02");
        } else if (i == 2) {
            list.add("002MW01");
            list.add("002MW02");
            list.add("002MW02");
        } else {
            list.add("003MW01");
            list.add("003MW02");
            list.add("003MW02");
        }
        return list;
    }

    public List<String> getItemCode(String i) {
        ArrayList<String> list = new ArrayList<String>();
        if (i.equals("880000021")) {
            list.add("F02002TJ60A");
            list.add("F02002TJ60B");
        } else if (i.equals("880000020")) {
            list.add("A02002TJ60A");
            list.add("A02002TJ60B");
        } else if (i.equals("480000020")) {
            list.add("B02002TJ60A");
            list.add("B02002TJ60B");
        } else {
            list.add("C02002TJ60A");
            list.add("C02002TJ60B");
        }
        return list;
    }

    public List<ItemCode> getItemCode() {
        ArrayList<ItemCode> list = new ArrayList<ItemCode>();
        list.add(new ItemCode("F02002TJ60A", "880000021"));
        list.add(new ItemCode("F02002TJ60B", "880000021"));

        list.add(new ItemCode("A02002TJ60A", "880000020"));
        list.add(new ItemCode("A02002TJ60B", "880000020"));

        list.add(new ItemCode("B02002TJ60A", "480000020"));
        list.add(new ItemCode("B02002TJ60B", "480000020"));

        list.add(new ItemCode("C02002TJ60A", "4800000212"));
        list.add(new ItemCode("C02002TJ60B", "4800000212"));
        return list;
    }

    public List<WareHouse> getWareHouse() {
        ArrayList<WareHouse> list = new ArrayList<>();
        list.add(new WareHouse("MW01", "F02002TJ60A"));
        list.add(new WareHouse("MW02", "F02002TJ60A"));
        list.add(new WareHouse("MW03", "F02002TJ60A"));

        list.add(new WareHouse("MW04", "F02002TJ60B"));
        list.add(new WareHouse("MW05", "F02002TJ60B"));
        list.add(new WareHouse("MW06", "F02002TJ60B"));

        list.add(new WareHouse("MW07", "A02002TJ60A"));
        list.add(new WareHouse("MW08", "A02002TJ60A"));
        list.add(new WareHouse("MW09", "A02002TJ60A"));

        list.add(new WareHouse("MW10", "A02002TJ60B"));
        list.add(new WareHouse("MW11", "A02002TJ60B"));
        list.add(new WareHouse("MW12", "A02002TJ60B"));

        list.add(new WareHouse("WH01", "B02002TJ60A"));
        list.add(new WareHouse("WH02", "B02002TJ60A"));
        list.add(new WareHouse("WH03", "B02002TJ60A"));

        list.add(new WareHouse("WH04", "B02002TJ60B"));
        list.add(new WareHouse("WH05", "B02002TJ60B"));
        list.add(new WareHouse("WH06", "B02002TJ60B"));

        list.add(new WareHouse("WH07", "C02002TJ60A"));
        list.add(new WareHouse("WH08", "C02002TJ60A"));
        list.add(new WareHouse("WH09", "C02002TJ60A"));

        list.add(new WareHouse("WH10", "C02002TJ60B"));
        list.add(new WareHouse("WH11", "C02002TJ60B"));
        list.add(new WareHouse("WH12", "C02002TJ60B"));
        return list;
    }

    public List<StackModel> getStackList() {
        ArrayList<StackModel> list = new ArrayList<StackModel>();
        list.add(new StackModel("001MW01","MW01"));
        list.add(new StackModel("002MW01","MW01"));
        list.add(new StackModel("003MW01","MW01"));
        list.add(new StackModel("004MW01","MW01"));

        list.add(new StackModel("001MW02","MW02"));
        list.add(new StackModel("002MW02","MW02"));
        list.add(new StackModel("003MW02","MW02"));
        list.add(new StackModel("004MW02","MW02"));

        list.add(new StackModel("001MW03","MW03"));
        list.add(new StackModel("002MW03","MW03"));
        list.add(new StackModel("003MW03","MW03"));
        list.add(new StackModel("004MW03","MW03"));

        list.add(new StackModel("001MW04","MW04"));
        list.add(new StackModel("002MW04","MW04"));
        list.add(new StackModel("003MW04","MW04"));
        list.add(new StackModel("004MW04","MW04"));

        list.add(new StackModel("001MW05","MW05"));
        list.add(new StackModel("002MW05","MW05"));
        list.add(new StackModel("003MW05","MW05"));
        list.add(new StackModel("004MW05","MW05"));

        list.add(new StackModel("001MW06","MW06"));
        list.add(new StackModel("002MW06","MW06"));
        list.add(new StackModel("003MW06","MW06"));
        list.add(new StackModel("004MW06","MW06"));

        list.add(new StackModel("001MW07","MW07"));
        list.add(new StackModel("002MW07","MW07"));
        list.add(new StackModel("003MW07","MW07"));
        list.add(new StackModel("004MW07","MW07"));

        list.add(new StackModel("001MW08","MW08"));
        list.add(new StackModel("002MW08","MW08"));
        list.add(new StackModel("003MW08","MW08"));
        list.add(new StackModel("004MW08","MW08"));

        list.add(new StackModel("001MW09","MW09"));
        list.add(new StackModel("002MW09","MW09"));
        list.add(new StackModel("003MW09","MW09"));
        list.add(new StackModel("004MW09","MW09"));

        list.add(new StackModel("001MW010","MW10"));
        list.add(new StackModel("002MW010","MW10"));
        list.add(new StackModel("003MW010","MW10"));
        list.add(new StackModel("004MW010","MW10"));

        list.add(new StackModel("001MW011","MW11"));
        list.add(new StackModel("002MW011","MW11"));
        list.add(new StackModel("003MW011","MW11"));
        list.add(new StackModel("004MW011","MW11"));

        list.add(new StackModel("001MW012","MW12"));
        list.add(new StackModel("002MW012","MW12"));
        list.add(new StackModel("003MW012","MW12"));
        list.add(new StackModel("004MW012","MW12"));

        list.add(new StackModel("001WH001","WH01"));
        list.add(new StackModel("002WH001","WH01"));
        list.add(new StackModel("003WH001","WH01"));
        list.add(new StackModel("004WH001","WH01"));


        list.add(new StackModel("001WH002","WH02"));
        list.add(new StackModel("002WH002","WH02"));
        list.add(new StackModel("003WH002","WH02"));
        list.add(new StackModel("004WH002","WH02"));

        list.add(new StackModel("001WH003","WH03"));
        list.add(new StackModel("002WH003","WH03"));
        list.add(new StackModel("003WH003","WH03"));
        list.add(new StackModel("004WH003","WH03"));

        list.add(new StackModel("001WH004","WH04"));
        list.add(new StackModel("002WH004","WH04"));
        list.add(new StackModel("003WH004","WH04"));
        list.add(new StackModel("004WH004","WH04"));

        list.add(new StackModel("001WH005","WH05"));
        list.add(new StackModel("002WH005","WH05"));
        list.add(new StackModel("003WH005","WH05"));
        list.add(new StackModel("004WH005","WH05"));

        list.add(new StackModel("001WH006","WH06"));
        list.add(new StackModel("002WH006","WH06"));
        list.add(new StackModel("003WH006","WH06"));
        list.add(new StackModel("004WH006","WH06"));

        list.add(new StackModel("001WH007","WH07"));
        list.add(new StackModel("002WH007","WH07"));
        list.add(new StackModel("003WH007","WH07"));
        list.add(new StackModel("004WH007","WH07"));

        list.add(new StackModel("001WH008","WH08"));
        list.add(new StackModel("002WH008","WH08"));
        list.add(new StackModel("003WH008","WH08"));
        list.add(new StackModel("004WH008","WH08"));

        list.add(new StackModel("001WH009","WH09"));
        list.add(new StackModel("002WH009","WH09"));
        list.add(new StackModel("003WH009","WH09"));
        list.add(new StackModel("004WH009","WH09"));


        list.add(new StackModel("001WH010","WH10"));
        list.add(new StackModel("002WH010","WH10"));
        list.add(new StackModel("003WH010","WH10"));
        list.add(new StackModel("004WH010","WH10"));


        list.add(new StackModel("001WH011","WH11"));
        list.add(new StackModel("002WH011","WH11"));
        list.add(new StackModel("003WH011","WH11"));
        list.add(new StackModel("004WH011","WH11"));


        list.add(new StackModel("001WH012","WH12"));
        list.add(new StackModel("002WH012","WH12"));
        list.add(new StackModel("003WH012","WH12"));
        list.add(new StackModel("004WH012","WH12"));

        return list;
    }


}
