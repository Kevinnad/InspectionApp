package com.example.androidassignment.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidassignment.R;
import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.databinding.ActivityHomeBinding;
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

        binding.button4.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));


    }

    @Override
    public void initViewModel() {

    }
}