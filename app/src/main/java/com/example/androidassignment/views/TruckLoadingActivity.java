package com.example.androidassignment.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidassignment.NothingSelectedSpinnerAdapter;
import com.example.androidassignment.R;
import com.example.androidassignment.adapter.InspectionAdapter;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.TruckLoadingDataModel;
import com.example.androidassignment.databinding.ActivityPreLoadingInspectionBinding;
import com.example.androidassignment.databinding.ActivityTruckLoadingInspectionBinding;
import com.example.androidassignment.viewmodel.TruckLoadingViewModel;

import java.util.ArrayList;

public class TruckLoadingActivity extends AppCompatActivity {

    ActivityTruckLoadingInspectionBinding binding;
    TruckLoadingViewModel truckLoadingViewModel;
    ArrayList<Data> itemList;
    int previousID = 0;
    boolean isNew = true;
    private TruckLoadingDataModel truckLoadingDataModel;
    ArrayAdapter<String> stackAdapter;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayAdapter<String> orderNumAdapter;
    ArrayAdapter<String> warehouseAdapter;
    ArrayAdapter<String> itemCodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTruckLoadingInspectionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolbar.btnBack.setOnClickListener(view1 -> finish());
        binding.toolbar.btnHome.setOnClickListener(view1 ->  startActivity(new Intent(this, HomeActivity.class)));

        truckLoadingViewModel = new ViewModelProvider(this).get(TruckLoadingViewModel.class);

        createDataBase();
        observers();
        loadData();
        initAutocompleteAdapter();
        initInspectionAdapter();
    }

    void createDataBase() {

        truckLoadingViewModel.dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
    }

    void loadData() {
        truckLoadingViewModel.getLastInspection();
    }


    private void initOtherSpinnerData(int i) {

        final ArrayList<String> list3 = new ArrayList<String>();
        if (i == 1) {
            list3.add("001MW01");
            list3.add("001MW02");
            list3.add("001MW02");
        }
        else if (i == 2) {
            list3.add("002MW01");
            list3.add("002MW02");
            list3.add("002MW02");
        }else {
            list3.add("003MW01");
            list3.add("003MW02");
            list3.add("003MW02");
        }

        stackAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list3);
        binding.spStack.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        stackAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if(truckLoadingDataModel != null){
            binding.spStack.setSelection(truckLoadingDataModel.getStack());
        }

    }

    private void initAutocompleteAdapter() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("20/B124/RAK/0000001/2021");
        list.add("70/B124/RAK/0000001/2022");
        autoCompleteAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.autoCompleteLoadingNum.setAdapter(autoCompleteAdapter);

        binding.autoCompleteLoadingNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                initSpinnerData(i);
            }
        });
    }


    private void setWareHouse(int i) {

        final ArrayList<String> list2 = new ArrayList<String>();
        if (i == 1) {
            list2.add("MW01");
            list2.add("MW02");
            list2.add("MW03");
        }else {
            list2.add("SW01");
            list2.add("SW02");
            list2.add("SW03");
        }
        warehouseAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list2);
        binding.spWarehouse.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        warehouseAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if(truckLoadingDataModel != null){
            binding.spWarehouse.setSelection(truckLoadingDataModel.getWareHouse());
        }
        binding.spWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initOtherSpinnerData(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSpinnerData(int i) {
        final ArrayList<String> list = new ArrayList<String>();
        if (i == 0) {
            list.add("880000021");
            list.add("880000020");
        } else {
            list.add("480000020");
            list.add("4800000212");
        }

        orderNumAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.spOrderNum.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        orderNumAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if(truckLoadingDataModel != null){
            binding.spOrderNum.setSelection(truckLoadingDataModel.getOrderNumber());
        }
        binding.spOrderNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setWareHouse(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initInspectionAdapter() {

        binding.btSubmit.setOnClickListener(view -> {
            if (isValid())
                saveInspection(false);
        });

        binding.btNext.setOnClickListener(view -> {
            if (isValid())
                saveInspection(true);
        });

        binding.btPrev.setOnClickListener(view -> {
            if (truckLoadingViewModel.lastId > 0  && truckLoadingViewModel.currentId > 1)
                truckLoadingViewModel.getPreviousData();
            else
                Toast.makeText(this, "No Previous Data", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean isValid() {
        if (binding.autoCompleteLoadingNum.getText() == null || binding.autoCompleteLoadingNum.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Input Rake loading number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spOrderNum.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Order number", Toast.LENGTH_SHORT).show();
            return false;
        }  else if (binding.spWarehouse.getSelectedItem() == null || binding.spWarehouse.getSelectedItemPosition() ==0) {
            Toast.makeText(this,
                    "Select Warehouse", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spStack.getSelectedItem() == null || binding.spStack.getSelectedItemPosition() ==0) {
            Toast.makeText(this,
                    "Select Stack", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etNoTruck.getText() == null || binding.etNoTruck.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Number of Trucks", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etTruckID.getText() == null || binding.etTruckID.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Truck Id", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etTruckNo.getText() == null || binding.etTruckNo.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Truck Number", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etNoOfBags.getText() == null || binding.etNoOfBags.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Number of Bags", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etBagStickQuality.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Input Bag Stick Quality", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etChelliCount.getText() == null || binding.etChelliCount.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter ChelliCount", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    void observers() {
        truckLoadingViewModel.inserSuccessLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        truckLoadingViewModel.previousInspection.observe(this, new Observer<TruckLoadingDataModel>() {
            @Override
            public void onChanged(TruckLoadingDataModel dataModel) {
                previousID = dataModel.getId();
                isNew = false;
                setInspection(dataModel);
            }
        });
    }
    void setInspection(TruckLoadingDataModel truckLoadingDataModel) {
        this.truckLoadingDataModel = truckLoadingDataModel;
        binding.autoCompleteLoadingNum.setText(truckLoadingDataModel.getRakeLoadingNumber());
        setOrderNumber(truckLoadingDataModel.getRakeLoadingNumber());
        binding.etTruckNo.setText(truckLoadingDataModel.getTruckNumber());
        binding.etTruckID.setText(String.valueOf(truckLoadingDataModel.getTruckID()));
        binding.etNoTruck.setText(String.valueOf(truckLoadingDataModel.getNoOfTrucks()));
        binding.etChelliCount.setText(truckLoadingDataModel.getChelliCount());
        binding.etNoOfBags.setText(truckLoadingDataModel.getNumOfBags());
        if(truckLoadingDataModel.getQuality() == 0)
        {
            binding.rbGood.setChecked(true);
        }
        else
            binding.rbAverage.setChecked(true);
        binding.tvSync.setVisibility((truckLoadingDataModel.isSync()) ? View.VISIBLE : View.GONE);
        toggleAction(!truckLoadingDataModel.isSync());
    }

    void setOrderNumber(String rake){
        if(rake.equals("20/B124/RAK/0000001/2021")){
            initSpinnerData(0);
        }else{
            initSpinnerData(1);
        }
    }

    void toggleAction(boolean isEnable) {
        binding.autoCompleteLoadingNum.setEnabled(isEnable);
        binding.spOrderNum.setEnabled(isEnable);
        binding.spWarehouse.setEnabled(isEnable);
        binding.spStack.setEnabled(isEnable);
        binding.tvSync.setEnabled(isEnable);
        binding.etTruckNo.setEnabled(isEnable);
        binding.etChelliCount.setEnabled(isEnable);
        binding.etNoOfBags.setEnabled(isEnable);
        binding.etBagStickQuality.setEnabled(isEnable);
        binding.etTruckID.setEnabled(isEnable);
    }

    void saveInspection(boolean isNext) {
        TruckLoadingDataModel truckLoadingDataModel = new TruckLoadingDataModel();
        truckLoadingDataModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        truckLoadingDataModel.setOrderNumber(binding.spOrderNum.getSelectedItemPosition());
        truckLoadingDataModel.setWareHouse(binding.spWarehouse.getSelectedItemPosition());
        truckLoadingDataModel.setStack(binding.spStack.getSelectedItemPosition());
        truckLoadingDataModel.setNoOfTrucks(Integer.parseInt(binding.etNoTruck.getText().toString()));
        truckLoadingDataModel.setTruckID(Integer.parseInt(binding.etTruckID.getText().toString()));
        truckLoadingDataModel.setNumOfBags(binding.etNoOfBags.getText().toString());
        truckLoadingDataModel.setChelliCount(binding.etChelliCount.getText().toString());
        truckLoadingDataModel.setTruckNumber(binding.etTruckNo.getText().toString());
        if(binding.rbAverage.isChecked())
            truckLoadingDataModel.setQuality(1);
        else
            truckLoadingDataModel.setQuality(0);


        
        if (previousID != 0)
            truckLoadingDataModel.setId(previousID);

        if (isNext) {
            if (truckLoadingViewModel.lastId > 0 && truckLoadingViewModel.currentId + 1 <= truckLoadingViewModel.lastId) {
                truckLoadingViewModel.getNextData();
            } else{
                callSaveInspection(truckLoadingDataModel);
                resetInspectionScreen();
            }
        } else {
            truckLoadingDataModel.setSync(true);
            callSaveInspection(truckLoadingDataModel);
            binding.tvSync.setVisibility(View.VISIBLE);
        }
    }

    void callSaveInspection(TruckLoadingDataModel mtruckLoadingDataModel){
        if(binding.tvSync.getVisibility() == View.GONE){
            this.truckLoadingDataModel = mtruckLoadingDataModel;
            truckLoadingViewModel.addData(mtruckLoadingDataModel);
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
        }
    }

    void resetInspectionScreen() {
        previousID = 0;
        binding.spWarehouse.setSelection(0);
        binding.spStack.setSelection(0);
        binding.tvSync.setVisibility(View.GONE);
        binding.etNoTruck.setText("");
        binding.etTruckID.setText("");
        binding.etTruckNo.setText("");
        binding.etNoOfBags.setText("");
        binding.etChelliCount.setText("");
        binding.etBagStickQuality.clearCheck();
        isNew = true;
        truckLoadingDataModel = null;
        toggleAction(true);
    }
}