package com.example.androidassignment.views;

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
import com.example.androidassignment.adapter.InspectionAdapter;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;
import com.example.androidassignment.databinding.ActivityPreLoadingInspectionBinding;
import com.example.androidassignment.viewmodel.InspectionViewModel;

import java.util.ArrayList;

public class InspectionActivity extends AppCompatActivity {

    ActivityPreLoadingInspectionBinding binding;
    InspectionViewModel inspectionViewModel;
    ItemCodeAttributesDataModel itemList;
    int previousID = 0;
    boolean isNew = true;
    private int currentVisibleId;
    ArrayAdapter<String> stackAdapter;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayAdapter<String> orderNumAdapter;
    ArrayAdapter<String> warehouseAdapter;
    ArrayAdapter<String> itemCodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreLoadingInspectionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        inspectionViewModel = new ViewModelProvider(this).get(InspectionViewModel.class);

        createDataBase();
        observers();
        loadData();
        initAutocompleteAdapter();
        initInspectionAdapter();
    }

    void createDataBase() {

        inspectionViewModel.dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
    }

    void loadData() {
        inspectionViewModel.getLastInspection();
    }


    private void initOtherSpinnerData(int i) {

        final ArrayList<String> list3 = new ArrayList<String>();
        if(i == 1){
            list3.add("001MW01");
            list3.add("001MW02");
            list3.add("001MW02");
        }if(i == 2){
            list3.add("002MW01");
            list3.add("002MW02");
            list3.add("002MW02");
        }else{
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


    }

    void setAdapter(ArrayList<Data> dataList) {
        binding.tvMax.setVisibility(View.VISIBLE);
        binding.tvMin.setVisibility(View.VISIBLE);
        binding.tvActual.setVisibility(View.VISIBLE);
        binding.rvList.setAdapter(new InspectionAdapter(dataList));
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

    private void setItemCode(int i) {


        final ArrayList<String> list1 = new ArrayList<String>();

        if (i == 1) {
            list1.add("F02002TJ60A");
            list1.add("F02002TJ60B");
        } else {
            list1.add("A02002TJ60A");
            list1.add("A02002TJ60B");
        }

        itemCodeAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list1);
        binding.spItemCode.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        itemCodeAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        binding.spItemCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView == null || adapterView.getItemAtPosition(i) == null || adapterView.getItemAtPosition(i).equals("[Select]")) {

                } else if (isNew) {
                    itemList = inspectionViewModel.formInspectionItemList(i, list1);
                    setAdapter(itemList.dataList);
                }

                setWareHouse(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setWareHouse(int i) {

        final ArrayList<String> list2 = new ArrayList<String>();
        if(i == 1){
            list2.add("MW01");
            list2.add("MW02");
            list2.add("MW03");
        }else{
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

        binding.spOrderNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setItemCode(i);
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
            if (inspectionViewModel.lastId > 0)
                inspectionViewModel.getPreviousData();
            else
                Toast.makeText(this, "No Previous Data", Toast.LENGTH_LONG).show();
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
        } else if (binding.spOrderNum.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Order number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spItemCode.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Itemcode", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spWarehouse.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Warehouse", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spStack.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Stack", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void observers() {
        inspectionViewModel.inserSuccessLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        inspectionViewModel.previousInspection.observe(this, new Observer<InspectionDataModel>() {
            @Override
            public void onChanged(InspectionDataModel dataModel) {
                previousID = dataModel.getId();
                isNew = false;
                setInspection(dataModel);
            }
        });
    }

    void setInspection(InspectionDataModel inspectionDataModel) {
        binding.autoCompleteLoadingNum.setText(inspectionDataModel.getRakeLoadingNumber());
        binding.spItemCode.setSelection(inspectionDataModel.getItemCode());
        binding.spWarehouse.setSelection(inspectionDataModel.getWareHouse());
        setAdapter(inspectionDataModel.getItems());
    }

    void saveInspection(boolean isNext) {
        InspectionDataModel inspectionDataModel = new InspectionDataModel();
        inspectionDataModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        inspectionDataModel.setOrderNumber(binding.spOrderNum.getSelectedItem().toString());
        inspectionDataModel.setItemCode(binding.spItemCode.getSelectedItemPosition());
        inspectionDataModel.setWareHouse(binding.spWarehouse.getSelectedItemPosition());
        inspectionDataModel.setItems(itemList.dataList);
        if (previousID != 0)
            inspectionDataModel.setId(previousID);


        if (isNext) {
            if (inspectionViewModel.lastId > 0 && inspectionViewModel.currentId + 1 <= inspectionViewModel.lastId) {

                inspectionViewModel.getNextData();

            } else
                resetInspectionScreen();
        }

        inspectionViewModel.addData(inspectionDataModel);

    }

    void resetInspectionScreen() {
        setItemCode(0);
        binding.rvList.setAdapter(null);
        binding.tvMin.setVisibility(View.GONE);
        binding.tvMax.setVisibility(View.GONE);
        binding.tvActual.setVisibility(View.GONE);
        isNew = true;
        Toast.makeText(this, "Data Saved", Toast.LENGTH_LONG).show();
    }
}