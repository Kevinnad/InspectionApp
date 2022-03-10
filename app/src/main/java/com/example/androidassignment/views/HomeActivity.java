package com.example.androidassignment.views;

import static com.example.androidassignment.dataStore.CommonDataStore.RAKE_LOADING_NO;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.base.BaseRepository;
import com.example.androidassignment.dataStore.CommonDataStore;
import com.example.androidassignment.databinding.ActivityHomeBinding;
import com.example.androidassignment.repository.InspectionRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    ActivityHomeBinding binding;
    String selectedRakeLoading;

    @Override
    public ActivityHomeBinding getBinding() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        return binding;    }

    @Override
    public void initView() {

        binding.btRakeLoading.setOnClickListener(view -> navigateTo(RakeLoadingActivity.class));
        binding.btRakeUnloading.setOnClickListener(view -> navigateTo(RakeUnloadingActivity.class));

        binding.toolbar.toolbarTitle.setText("Home");
        binding.toolbar.btnBack.setOnClickListener(view -> finish());
        binding.toolbar.btnHome.setVisibility(View.GONE);

        InspectionRepository inspectionRepository = new InspectionRepository(null);

        final List<String> list = inspectionRepository.getRackLoadingData();

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.inputRakeNumber.setAdapter(autoCompleteAdapter);

        selectedRakeLoading = CommonDataStore.getStringInPrefernce(this,RAKE_LOADING_NO);
        if(!TextUtils.isEmpty(selectedRakeLoading)){
            binding.inputRakeNumber.setText(selectedRakeLoading);
        }

        binding.inputRakeNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRakeLoading = list.get(i);
                CommonDataStore.saveStringInPrefernce(HomeActivity.this,RAKE_LOADING_NO,list.get(i));
            }
        });

    }

    private void navigateTo(Class<?> c){
        if(TextUtils.isEmpty(selectedRakeLoading)){
            Toast.makeText(this,
                    "Please select the Rake loading Number", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(HomeActivity.this, c);
            intent.putExtra(CommonDataStore.ORDER_NO,selectedRakeLoading);
            startActivity(intent);
        }

    }

    @Override
    public void initViewModel() {

    }
}