package com.example.androidassignment.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.databinding.ActivityRakeUnloadingBinding;
import com.example.androidassignment.databinding.ActivityRakeUnloadingBinding;

public class RakeUnloadingActivity extends BaseActivity<ActivityRakeUnloadingBinding> {

    ActivityRakeUnloadingBinding binding;

    @Override
    public ActivityRakeUnloadingBinding getBinding() {
        binding = ActivityRakeUnloadingBinding.inflate(getLayoutInflater());
        return binding;
    }

    @Override
    public void initView() {

        binding.toolbar.btnBack.setOnClickListener(view -> finish());
        binding.toolbar.btnHome.setOnClickListener(view ->  startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )));
        binding.toolbar.toolbarTitle.setText("Rake Unloading");

        binding.btTruckLoading.setOnClickListener(view -> startActivity(new Intent(this, UnloadingTruckLoadingActivity.class)));
        binding.btWagonPreLoading.setOnClickListener(view -> startActivity(new Intent(this, WagonPreLoadingActivity.class)));
        binding.btTruckUnloading.setOnClickListener(view -> startActivity(new Intent(this, TruckUnloadingActivity.class)));
        binding.btStackQuality.setOnClickListener(view -> startActivity(new Intent(this, StackQualityInspectionActivity.class)));
    }

    @Override
    public void initViewModel() {

    }
}