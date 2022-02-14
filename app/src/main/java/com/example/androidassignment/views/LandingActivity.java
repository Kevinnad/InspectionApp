package com.example.androidassignment.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidassignment.R;
import com.example.androidassignment.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {

    ActivityLandingBinding activityLandingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLandingBinding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(activityLandingBinding.getRoot());

        activityLandingBinding.btPreLoadingInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this,InspectionActivity.class));
            }
        });
        activityLandingBinding.btTruckLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this,TruckLoadingActivity.class));
            }
        });
        activityLandingBinding.btTestInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this,TestInspectionActivity.class));
            }
        });
    }
}