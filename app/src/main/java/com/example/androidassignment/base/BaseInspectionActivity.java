package com.example.androidassignment.base;

import android.view.View;
import android.widget.Button;

import com.example.androidassignment.R;

public abstract class BaseInspectionActivity<B> extends BaseActivity<B>{

    @Override
    public void initView() {
        initInspectionView();
        initObserver();
        bindActionView();
    }

    public abstract void initInspectionView();

    public abstract void initObserver();

    public abstract void nextAction();

    public abstract void prevAction();

    public abstract void submitAction();

    void bindActionView(){
        Button prevButton = findViewById(R.id.bt_prev);
        prevButton.setOnClickListener(view -> prevAction());
        Button nextButton = findViewById(R.id.bt_next);
        nextButton.setOnClickListener(view -> nextAction());
        Button submitButton = findViewById(R.id.bt_submit);
        submitButton.setOnClickListener(view -> submitAction());
    }

}
