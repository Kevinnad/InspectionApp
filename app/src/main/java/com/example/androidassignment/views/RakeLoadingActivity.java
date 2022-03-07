package com.example.androidassignment.views;

import android.content.Intent;

import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.databinding.ActivityLandingBinding;

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
        activityLandingBinding.toolbar.btnHome.setOnClickListener(view ->  startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )));
        activityLandingBinding.toolbar.toolbarTitle.setText("Rake Loading");

        activityLandingBinding.btPreLoadingInspection.setOnClickListener(view -> startActivity(new Intent(RakeLoadingActivity.this, InspectionActivity.class)));
        activityLandingBinding.btTruckLoading.setOnClickListener(view -> startActivity(new Intent(RakeLoadingActivity.this, TruckLoadingActivity.class)));
        activityLandingBinding.btWagonPreLoading.setOnClickListener(view -> startActivity(new Intent(RakeLoadingActivity.this, WagonLoadingActivity.class)));
        activityLandingBinding.btTruckUnloading.setOnClickListener(view -> startActivity(new Intent(RakeLoadingActivity.this, TruckUnloadingActivity.class)));
        activityLandingBinding.btWagonPostLoading.setOnClickListener(view -> startActivity(new Intent(RakeLoadingActivity.this, WagonPostLoadingActivity.class)));
    }

    @Override
    public void initViewModel() {

    }
}