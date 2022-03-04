package com.example.androidassignment.views;

import android.content.Intent;

import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    ActivityHomeBinding binding;

    @Override
    public ActivityHomeBinding getBinding() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        return binding;    }

    @Override
    public void initView() {

        binding.btRakeLoading.setOnClickListener(view -> startActivity(new Intent(this, RakeLoadingActivity.class)));
        binding.btRakeUnloading.setOnClickListener(view -> startActivity(new Intent(this, RakeUnloadingActivity.class)));
        binding.toolbar.btnBack.setOnClickListener(view -> finish());
        binding.toolbar.btnHome.setOnClickListener(view ->  startActivity(new Intent(this, HomeActivity.class)));

    }

    @Override
    public void initViewModel() {

    }
}