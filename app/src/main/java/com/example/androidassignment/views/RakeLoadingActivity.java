package com.example.androidassignment.views;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.androidassignment.NothingSelectedSpinnerAdapter;
import com.example.androidassignment.R;
import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.dataStore.CommonDataStore;
import com.example.androidassignment.databinding.ActivityLandingBinding;
import com.example.androidassignment.repository.InspectionRepository;

import java.util.List;

public class RakeLoadingActivity extends BaseActivity<ActivityLandingBinding> {

    ActivityLandingBinding activityLandingBinding;

    @Override
    public ActivityLandingBinding getBinding() {
        activityLandingBinding = ActivityLandingBinding.inflate(getLayoutInflater());
        return activityLandingBinding;
    }

    @Override
    public void initView() {
        activityLandingBinding.toolbar.btnBack.setOnClickListener(view -> finish());
        activityLandingBinding.toolbar.btnHome.setOnClickListener(view ->  navigateTo(HomeActivity.class));
        activityLandingBinding.toolbar.toolbarTitle.setText("Rake Loading");

        activityLandingBinding.btPreLoadingInspection.setOnClickListener(view -> navigateTo(InspectionActivity.class));
        activityLandingBinding.btTruckLoading.setOnClickListener(view -> navigateTo(TruckLoadingActivity.class));
        activityLandingBinding.btWagonPreLoading.setOnClickListener(view -> navigateTo(WagonLoadingActivity.class));
        activityLandingBinding.btTruckUnloading.setOnClickListener(view -> navigateTo(TruckUnloadingActivity.class));
        activityLandingBinding.btWagonPostLoading.setOnClickListener(view -> navigateTo(WagonPostLoadingActivity.class));

        initSpinnerData();
    }

    private void navigateTo(Class<?> c){

            Intent intent = new Intent(RakeLoadingActivity.this, c);
            startActivity(intent);
    }

    @Override
    public void initViewModel() {

    }

    private void initSpinnerData() {

    }
}