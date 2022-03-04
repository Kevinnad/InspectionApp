package com.example.androidassignment.views;

import android.content.Intent;
import android.view.View;

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

        binding.toolbar.toolbarTitle.setText("Home");
        binding.toolbar.btnBack.setOnClickListener(view -> finish());
        binding.toolbar.btnHome.setVisibility(View.GONE);

    }

    @Override
    public void initViewModel() {

    }
}