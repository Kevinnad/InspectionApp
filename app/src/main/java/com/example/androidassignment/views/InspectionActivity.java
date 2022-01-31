package com.example.androidassignment.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidassignment.adapter.InspectionAdapter;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.databinding.ActivityPreLoadingInspectionBinding;
import com.example.androidassignment.viewmodel.InspectionViewModel;

import java.util.ArrayList;

public class InspectionActivity extends AppCompatActivity {

    ActivityPreLoadingInspectionBinding binding;
    InspectionViewModel inspectionViewModel;

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
        initSpinnerData();
    }

    void createDataBase() {

        inspectionViewModel.dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
    }

    void loadData() {
        inspectionViewModel.getLastInspection();
    }


    private void initSpinnerData() {


        final ArrayList<String> list = new ArrayList<String>();
        list.add("880000020");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.spOrderNum.setAdapter(adapter);

        final ArrayList<String> list1 = new ArrayList<String>();
        list1.add("F02002TJ60A");
        list1.add("000000100");
        list1.add("980000001");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list1);
        binding.spItemCode.setAdapter(adapter1);

        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("MW01");
        list2.add("000000100");
        list2.add("980000001");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list2);
        binding.spWarehouse.setAdapter(adapter2);

        final ArrayList<String> list3 = new ArrayList<String>();
        list3.add("001MW01");
        list3.add("000000100");
        list3.add("980000001");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list3);
        binding.spStack.setAdapter(adapter3);
    }

    private void initAutocompleteAdapter() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("20/B124/RAK/0000001/2021");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.autoCompleteLoadingNum.setAdapter(adapter);
    }

    private void initInspectionAdapter() {
        final ArrayList<DataModel> list = new ArrayList<>();
        list.add(new DataModel("HL", "76", "82", ""));
        list.add(new DataModel("KB", "0", "0.1", ""));
        list.add(new DataModel("RDGRN", "0", "12", ""));
        list.add(new DataModel("POTIA", "0", "5", ""));

        binding.rvList.setAdapter(new InspectionAdapter(list));

        binding.btSubmit.setOnClickListener(view -> {
            saveInspection(false);
        });

        binding.btNext.setOnClickListener(view -> {
            saveInspection(true);
        });

        binding.btPrev.setOnClickListener(view -> {
            if (inspectionViewModel.lastId > 0)
                inspectionViewModel.getPreviousData();
            else
                Toast.makeText(this, "No Previous Data", Toast.LENGTH_LONG).show();
        });
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
                setInspection(dataModel);
            }
        });
    }

    void setInspection(InspectionDataModel inspectionDataModel) {
        binding.autoCompleteLoadingNum.setText(inspectionDataModel.getRakeLoadingNumber());
    }

    void saveInspection(boolean isNext){
        InspectionDataModel inspectionDataModel = new InspectionDataModel();
        inspectionDataModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        inspectionDataModel.setOrderNumber(binding.spOrderNum.getSelectedItem().toString());
        inspectionViewModel.addData(inspectionDataModel);
        if(isNext)
            resetInspectionScreen();
    }

    void resetInspectionScreen(){
        binding.autoCompleteLoadingNum.setText("");
    }
}