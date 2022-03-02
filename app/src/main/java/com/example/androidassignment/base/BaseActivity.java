package com.example.androidassignment.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<B> extends AppCompatActivity {

    B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        setContentView(((ViewBinding)binding).getRoot());

        initViewModel();
        initView();
    }

    public abstract B getBinding();

    public abstract void initView();

    public abstract void initViewModel();

}
