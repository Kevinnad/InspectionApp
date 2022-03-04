package com.example.androidassignment.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.databinding.ActivityRakeUnloadingBinding;
import com.example.androidassignment.databinding.ActivityRakeUnloadingBinding;

public class RakeUnloadingActivity extends BaseActivity<ActivityRakeUnloadingBinding> {

    ActivityRakeUnloadingBinding ActivityRakeUnloadingBinding;

    @Override
    public ActivityRakeUnloadingBinding getBinding() {
        ActivityRakeUnloadingBinding = ActivityRakeUnloadingBinding.inflate(getLayoutInflater());
        return ActivityRakeUnloadingBinding;
    }

    @Override
    public void initView() {
        ActivityRakeUnloadingBinding.btTruckUnloading.setOnClickListener(view -> startActivity(new Intent(this, TruckUnloadingActivity.class)));
        ActivityRakeUnloadingBinding.btWagonPreLoading.setOnClickListener(view -> startActivity(new Intent(this, WagonPreLoadingActivity.class)));
        ActivityRakeUnloadingBinding.btWagonPostLoading.setOnClickListener(view -> startActivity(new Intent(this, WagonPostLoadingActivity.class)));
    }

    @Override
    public void initViewModel() {

    }
}