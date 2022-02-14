package com.example.androidassignment.views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidassignment.NothingSelectedSpinnerAdapter;
import com.example.androidassignment.R;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.TestInspectionModel;
import com.example.androidassignment.databinding.ActivityTestInspectionBinding;
import com.example.androidassignment.viewmodel.InspectionViewModel;

import java.util.ArrayList;
import java.util.List;

public class TestInspectionActivity extends AppCompatActivity {

    ActivityTestInspectionBinding binding;

    InspectionViewModel viewModel;
    ArrayAdapter<TestInspectionModel> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestInspectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(InspectionViewModel.class);
        createDataBase();
        insertDataInDatabase();


        observerData();
    }

    private void observerData() {

        viewModel.testInspection.observe(this, new Observer<List<TestInspectionModel>>() {

            @Override
            public void onChanged(List<TestInspectionModel> list) {

                if (!list.isEmpty())
                    initSpinnerAdapter(list);

            }
        });
    }

    private void insertDataInDatabase() {

        List<TestInspectionModel> list = new ArrayList<>();

        list.add(new TestInspectionModel("HL"));
        list.add(new TestInspectionModel("EE"));
        list.add(new TestInspectionModel("TT"));
        list.add(new TestInspectionModel("SS"));
        list.add(new TestInspectionModel("HH"));
        list.add(new TestInspectionModel("OO"));
        list.add(new TestInspectionModel("MN"));

        viewModel.insertAllTestInspectionData(list);


        getDataFromDataBase();


    }

    void createDataBase() {

        viewModel.dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
    }

    private void getDataFromDataBase() {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.getAllData();

            }
        }, 200);


    }

    private void initSpinnerAdapter(List<TestInspectionModel> list) {
        adapter = new ArrayAdapter<TestInspectionModel>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.spTest.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
    }
}