package com.example.androidassignment.dataStore;

import java.util.ArrayList;
import java.util.List;

public class CommonDataStore {

   public List<String> getRackLoadingData(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("20/B124/RAK/0000001/2021");
        list.add("70/B124/RAK/0000001/2022");
        return list;
    }

    public List<String> getOrderNumber(int item){
        ArrayList<String> list = new ArrayList<String>();
        if (item == 0) {
            list.add("880000021");
            list.add("880000020");
        } else {
            list.add("480000020");
            list.add("4800000212");
        }
        return list;
    }
}
