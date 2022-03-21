package com.example.androidassignment.base;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

    public void showAlertDialogForDelete(String message, OnAlertButtonClickListener onAlertButtonClickListener) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    onAlertButtonClickListener.onPositiveButton();
                    dialog.cancel();
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert = builder1.create();
        alert.show();
    }

    public interface OnAlertButtonClickListener{
        public void onPositiveButton();
    }

}
