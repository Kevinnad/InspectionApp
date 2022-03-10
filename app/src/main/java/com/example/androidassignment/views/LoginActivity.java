package com.example.androidassignment.views;


import android.content.Intent;

import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    ActivityLoginBinding binding;

    @Override
    public ActivityLoginBinding getBinding() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        return binding;
    }

    @Override
    public void initView() {

        binding.button4.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });



    }

    @Override
    public void initViewModel() {

    }
}