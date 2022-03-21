package com.example.androidassignment.views;

import android.content.Intent;
import android.view.View;

import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.databinding.ActivityHomeBinding;
import com.example.androidassignment.repository.InspectionRepository;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    ActivityHomeBinding binding;

    @Override
    public ActivityHomeBinding getBinding() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        return binding;    }

    @Override
    public void initView() {

        binding.btRakeLoading.setOnClickListener(view -> navigateTo(RakeLoadingActivity.class));
        binding.btRakeUnloading.setOnClickListener(view -> navigateTo(RakeUnloadingActivity.class));

        binding.toolbar.toolbarTitle.setText("Home");
        binding.toolbar.btnBack.setOnClickListener(view -> finish());
        binding.toolbar.btnHome.setVisibility(View.GONE);

        DataBaseProvider dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
        InspectionRepository inspectionRepository = new InspectionRepository(dataBaseProvider);



        binding.ivDelete.setOnClickListener(v -> {

            showAlertDialogForDelete("Are you sure, you want delete all the records?", new OnAlertButtonClickListener() {
                @Override
                public void onPositiveButton() {
                    inspectionRepository.deleteAllData();
                }
            });

        });

    }

    private void navigateTo(Class<?> c){
            Intent intent = new Intent(HomeActivity.this, c);
            startActivity(intent);
    }

    @Override
    public void initViewModel() {

    }
}