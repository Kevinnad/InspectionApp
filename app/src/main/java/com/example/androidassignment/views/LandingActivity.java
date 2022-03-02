package com.example.androidassignment.views;

import android.content.Intent;

import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.base.BaseInspectionActivity;
import com.example.androidassignment.databinding.ActivityLandingBinding;

public class LandingActivity extends BaseActivity<ActivityLandingBinding> {

    ActivityLandingBinding activityLandingBinding;

    @Override
    public ActivityLandingBinding getBinding() {
        activityLandingBinding = ActivityLandingBinding.inflate(getLayoutInflater());
        return activityLandingBinding;
    }

    @Override
    public void initView() {
        activityLandingBinding.btPreLoadingInspection.setOnClickListener(view -> startActivity(new Intent(LandingActivity.this, InspectionActivity.class)));
        activityLandingBinding.btTruckLoading.setOnClickListener(view -> startActivity(new Intent(LandingActivity.this, TruckLoadingActivity.class)));
        activityLandingBinding.btTruckUnloading.setOnClickListener(view -> startActivity(new Intent(LandingActivity.this, TruckUnloadingActivity.class)));
        activityLandingBinding.btWagonPreLoading.setOnClickListener(view -> startActivity(new Intent(LandingActivity.this, WagonPreLoadingActivity.class)));
    }

    @Override
    public void initViewModel() {

    }
}