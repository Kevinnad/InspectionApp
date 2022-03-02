package com.example.androidassignment.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.androidassignment.NothingSelectedSpinnerAdapter;
import com.example.androidassignment.R;
import com.example.androidassignment.adapter.InspectionAdapter;
import com.example.androidassignment.base.BaseInspectionActivity;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.TruckUnloadingModel;
import com.example.androidassignment.databinding.ActivityPreLoadingInspectionBinding;
import com.example.androidassignment.databinding.ActivityTruckUnloadingBinding;
import com.example.androidassignment.viewmodel.InspectionViewModel;
import com.example.androidassignment.viewmodel.TruckUnloadingViewmodel;

import java.util.ArrayList;
import java.util.List;

public class TruckUnloadingActivity extends BaseInspectionActivity<ActivityTruckUnloadingBinding> {

    ActivityTruckUnloadingBinding binding;
    TruckUnloadingViewmodel truckUnloadingViewmodel;
    ArrayList<Data> itemList;
    int previousID = 0;
    boolean isNew = true;
    private TruckUnloadingModel truckUnloadingModel;
    ArrayAdapter<String> stackAdapter;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayAdapter<String> orderNumAdapter;
    ArrayAdapter<String> warehouseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_unloading);
    }

    @Override
    public ActivityTruckUnloadingBinding getBinding() {
        binding = ActivityTruckUnloadingBinding.inflate(getLayoutInflater());
        return binding;
    }

    @Override
    public void initInspectionView() {
        loadData();
        initAutocompleteAdapter();
    }

    @Override
    public void initObserver() {
        observers();
    }

    @Override
    public void nextAction() {
        if (isValid())
            saveInspection(true);
    }

    @Override
    public void prevAction() {
        if (truckUnloadingViewmodel.lastId > 0  && truckUnloadingViewmodel.currentId > 1)
            truckUnloadingViewmodel.getPreviousData();
        else
            Toast.makeText(this, "No Previous Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void submitAction() {
        if (isValid())
            saveInspection(false);
    }

    @Override
    public void initViewModel() {
        truckUnloadingViewmodel = new ViewModelProvider(this).get(TruckUnloadingViewmodel.class);
        createDataBase();
        truckUnloadingViewmodel.initRepository();

    }

    void createDataBase() {
        truckUnloadingViewmodel.dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
    }

    void loadData() {
        truckUnloadingViewmodel.getLastInspection();
    }


    private void initOtherSpinnerData(int i) {

        final List<String> list3 = truckUnloadingViewmodel.inspectionRepository.getStackList(i);

        stackAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list3);
        binding.spStack.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        stackAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if(truckUnloadingModel != null){
            binding.spStack.setSelection(truckUnloadingModel.getStack());
        }

    }


    private void initAutocompleteAdapter() {
        final List<String> list = truckUnloadingViewmodel.inspectionRepository.getRackLoadingData();

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

        final List<String> list2 = truckUnloadingViewmodel.inspectionRepository.getWareHouseList(i);

        warehouseAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list2);
        binding.spWarehouse.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        warehouseAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if(truckUnloadingModel != null){
            binding.spWarehouse.setSelection(truckUnloadingModel.getWareHouse());
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
        final List<String> list = truckUnloadingViewmodel.inspectionRepository.getOrderNumber(i);

        orderNumAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.spOrderNum.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        orderNumAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if(truckUnloadingModel != null){
            binding.spOrderNum.setSelection(truckUnloadingModel.getOrderNumber());
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

    private boolean isValid() {
        if (binding.autoCompleteLoadingNum.getText() == null || binding.autoCompleteLoadingNum.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Input Rake loading number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spOrderNum.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Order number", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.spWarehouse.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Warehouse", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spStack.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Stack", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etTruckNo.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Truck Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.etNoOfBags.getText() == null || binding.etNoOfBags.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Number of Bags", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etBagStickQuality.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Input Bag Stick Quality", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }




    void observers() {
        truckUnloadingViewmodel.inserSuccessLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                truckUnloadingViewmodel.getLastInspection();
            }
        });

        truckUnloadingViewmodel.previousInspection.observe(this, new Observer<TruckUnloadingModel>() {
            @Override
            public void onChanged(TruckUnloadingModel dataModel) {
                truckUnloadingViewmodel.currentId = dataModel.getId();
                previousID = dataModel.getId();
                isNew = false;
                setInspection(dataModel);
            }
        });
    }
    void setInspection(TruckUnloadingModel truckUnloadingModel) {
        this.truckUnloadingModel = truckUnloadingModel;
        binding.autoCompleteLoadingNum.setText(truckUnloadingModel.getRakeLoadingNumber());
        setOrderNumber(truckUnloadingModel.getRakeLoadingNumber());
        binding.etTruckNo.setText(truckUnloadingModel.getTruckNumber());
        binding.etWagonNo.setText(truckUnloadingModel.getWagonNum());
        binding.etNoOfBags.setText(truckUnloadingModel.getUnloadedBags());
        if(truckUnloadingModel.getQuality() == 0)
        {
            binding.rbGood.setChecked(true);
        }
        else
            binding.rbAverage.setChecked(true);
        binding.tvSync.setVisibility((truckUnloadingModel.isSync()) ? View.VISIBLE : View.GONE);
        toggleAction(!truckUnloadingModel.isSync());
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
    }

    void saveInspection(boolean isNext) {
        TruckUnloadingModel truckUnloadingModel = new TruckUnloadingModel();
        truckUnloadingModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        binding.etNoOfBags.setText(truckUnloadingModel.getUnloadedBags());
        if(truckUnloadingModel.getQuality() == 0)
        {
            binding.rbGood.setChecked(true);
        }
        else
            binding.rbAverage.setChecked(true);
        truckUnloadingModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        truckUnloadingModel.setWagonNum(binding.etWagonNo.getText().toString());
        truckUnloadingModel.setOrderNumber(binding.spOrderNum.getSelectedItemPosition());
        truckUnloadingModel.setWareHouse(binding.spWarehouse.getSelectedItemPosition());
        truckUnloadingModel.setStack(binding.spStack.getSelectedItemPosition());
        if (previousID != 0)
            truckUnloadingModel.setId(previousID);

        if (isNext) {
            if (truckUnloadingViewmodel.lastId > 0 && truckUnloadingViewmodel.currentId + 1 <= truckUnloadingViewmodel.lastId) {
                truckUnloadingViewmodel.getNextData();
            } else{
                callSaveInspection(truckUnloadingModel);
                resetInspectionScreen();
            }
        } else {
            truckUnloadingModel.setSync(true);
            callSaveInspection(truckUnloadingModel);
            binding.tvSync.setVisibility(View.VISIBLE);
        }
    }

    void callSaveInspection(TruckUnloadingModel truckUnloadingModel){
        if(binding.tvSync.getVisibility() == View.GONE){
            this.truckUnloadingModel = truckUnloadingModel;
            truckUnloadingViewmodel.addData(truckUnloadingModel);
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
        }
    }

    void resetInspectionScreen() {
        previousID = 0;
        binding.spWarehouse.setSelection(0);
        binding.spStack.setSelection(0);
        binding.tvSync.setVisibility(View.GONE);
        isNew = true;
        truckUnloadingModel = null;
        toggleAction(true);
    }
}