package com.example.androidassignment.dataStore;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class CommonDataStore {

    static String MY_PREFS_NAME = "Inspection_Pref";
    public static String RAKE_LOADING_NO = "Rake_loading";
    public static String ORDER_NO = "Order_number";

   public List<String> getRackLoadingData(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("20/B124/RAK/0000001/2021");
        list.add("20/B125/RAK/0000002/2022");
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

    public List<String> getOrderNumber(String item){
        ArrayList<String> list = new ArrayList<String>();
        if (item.equals("20/B124/RAK/0000001/2021")) {
            list.add("880000021");
            list.add("880000020");
        } else {
            list.add("480000020");
            list.add("4800000212");
        }
        return list;
    }


    public static void saveStringInPrefernce(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringInPrefernce(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return pref.getString(key,"");
    }
}
