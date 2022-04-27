package com.example.androidassignment.dataStore;

import java.util.ArrayList;
import java.util.List;

public class TruckUnloadingDataStore {
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
    public List<String> getWagonNumberList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("MP2345");
        list.add("MP2366");
        list.add("MP2898");
        list.add("MP90687");

        return list;
    }

    public List<String> getTruckNumberList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("SR2345");
        list.add("SR4366");
        list.add("SR2898");
        list.add("SR90687");

        return list;
    }
}
