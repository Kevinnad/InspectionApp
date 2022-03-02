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

}
