package com.example.androidassignment.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidassignment.NothingSelectedSpinnerAdapter;
import com.example.androidassignment.R;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.TruckLoadingDataModel;
import com.example.androidassignment.databinding.ActivityRakeUnloadingTruckLoadingBinding;
import com.example.androidassignment.databinding.ActivityTruckLoadingInspectionBinding;
import com.example.androidassignment.viewmodel.TruckLoadingViewModel;

import java.util.ArrayList;
import java.util.List;

public class RakeUnloadingTruckLoadingActivity extends AppCompatActivity {

    ActivityRakeUnloadingTruckLoadingBinding binding;
    TruckLoadingViewModel truckLoadingViewModel;
    ArrayList<Data> itemList;
    int previousID = 0;
    boolean isNew = true;
    private TruckLoadingDataModel truckLoadingDataModel;
    ArrayAdapter<String> stackAdapter;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayAdapter<String> wagonNumAdapter;
    ArrayAdapter<String> warehouseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRakeUnloadingTruckLoadingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolbar.btnBack.setOnClickListener(view1 -> finish());
        binding.toolbar.btnHome.setOnClickListener(view1 ->  startActivity(new Intent(this, HomeActivity.class)));
        binding.toolbar.toolbarTitle.setText("Truck Loading");

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



    private void initSpinnerData(int i) {
        final ArrayList<String> list = new ArrayList<String>();
        if (i == 0) {
            list.add("880000021");
            list.add("880000020");
        } else {
            list.add("480000020");
            list.add("4800000212");
        }

       binding.spOrderNum.setText(list.get(0));
        initWagonSpinnerData();

    } private void initWagonSpinnerData() {

        final List<String> list3 = truckLoadingViewModel.getWagonNumberList();

        wagonNumAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list3);
        binding.spWagonNo.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        wagonNumAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

    }
    private void initInspectionAdapter() {

        binding.btSubmit.setOnClickListener(view -> {
            if (truckLoadingViewModel.lastId > 0){
                truckLoadingDataModel.setSync(true);
                truckLoadingViewModel.syncAllData();
            }else
                Toast.makeText(this, "No Data to Sync", Toast.LENGTH_SHORT).show();
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
        } else if (binding.etTruckID.getText() == null || binding.etTruckID.getText().toString().isEmpty()) {
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
        }else if (binding.etBagQuality.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Input Bag Stitch Quality", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    void observers() {
        truckLoadingViewModel.inserSuccessLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (truckLoadingViewModel.lastId > 0 && truckLoadingViewModel.currentId + 1 <= truckLoadingViewModel.lastId){

                }else{
                    truckLoadingViewModel.getLastInspection();
                }

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
        binding.etNoOfBags.setText(truckLoadingDataModel.getNumOfBags());
        if(truckLoadingDataModel.getQuality() == 0)
        {
            binding.rbBagGood.setChecked(true);
        }
        else
            binding.rbBagAverage.setChecked(true);
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
        binding.tvSync.setEnabled(isEnable);
        binding.etTruckNo.setEnabled(isEnable);
        binding.etNoOfBags.setEnabled(isEnable);
        binding.etBagQuality.setEnabled(isEnable);
        binding.etTruckID.setEnabled(isEnable);
        binding.btSubmit.setEnabled(isEnable);
    }

    void saveInspection(boolean isNext) {
        TruckLoadingDataModel truckLoadingDataModel = new TruckLoadingDataModel();
        truckLoadingDataModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        truckLoadingDataModel.setOrderNumber(0);

        truckLoadingDataModel.setTruckID(Integer.parseInt(binding.etTruckID.getText().toString()));
        truckLoadingDataModel.setNumOfBags(binding.etNoOfBags.getText().toString());
        truckLoadingDataModel.setTruckNumber(binding.etTruckNo.getText().toString());
        if(binding.rbBagAverage.isChecked())
            truckLoadingDataModel.setQuality(1);
        else
            truckLoadingDataModel.setQuality(0);

        if (previousID != 0)
            truckLoadingDataModel.setId(previousID);

        if (isNext) {
            if (truckLoadingViewModel.lastId > 0 && truckLoadingViewModel.currentId + 1 <= truckLoadingViewModel.lastId) {
                truckLoadingViewModel.getNextData();
            } else{

                if (this.truckLoadingDataModel != null && this.truckLoadingDataModel.isSync()) {
                    Toast.makeText(this,
                            "No Next Data", Toast.LENGTH_SHORT).show();
                }else {
                    callSaveInspection(truckLoadingDataModel);
                    resetInspectionScreen();
                }
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

        binding.tvSync.setVisibility(View.GONE);

        binding.etTruckID.setText("");
        binding.etTruckNo.setText("");
        binding.etNoOfBags.setText("");
        binding.etBagQuality.clearCheck();
        binding.etBagQuality.clearCheck();
        isNew = true;
        truckLoadingDataModel = null;
        toggleAction(true);
    }
}