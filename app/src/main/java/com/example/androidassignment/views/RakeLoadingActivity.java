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
        activityLandingBinding.btPreLoadingInspection.setOnClickListener(view -> startActivity(new Intent(RakeLoadingActivity.this, InspectionActivity.class)));
        activityLandingBinding.btTruckLoading.setOnClickListener(view -> startActivity(new Intent(RakeLoadingActivity.this, TruckLoadingActivity.class)));
    }

    @Override
    public void initViewModel() {

    }
}