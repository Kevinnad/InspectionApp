package com.example.androidassignment.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidassignment.NothingSelectedSpinnerAdapter;
import com.example.androidassignment.R;
import com.example.androidassignment.adapter.InspectionAdapter;
import com.example.androidassignment.base.BaseActivity;
import com.example.androidassignment.base.BaseInspectionActivity;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCodeAttributesDataModel;
import com.example.androidassignment.databinding.ActivityPreLoadingInspectionBinding;
import com.example.androidassignment.viewmodel.InspectionViewModel;

import java.util.ArrayList;
import java.util.List;

public class InspectionActivity extends BaseInspectionActivity<ActivityPreLoadingInspectionBinding> {

    ActivityPreLoadingInspectionBinding binding;
    InspectionViewModel inspectionViewModel;
    ArrayList<Data> itemList;
    int previousID = 0;
    boolean isNew = true;
    private InspectionDataModel inspectionDataModel;
    ArrayAdapter<String> stackAdapter;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayAdapter<String> orderNumAdapter;
    ArrayAdapter<String> warehouseAdapter;
    ArrayAdapter<String> itemCodeAdapter;
    InspectionAdapter inspectionAdapter;

    @Override
    public ActivityPreLoadingInspectionBinding getBinding() {
        binding = ActivityPreLoadingInspectionBinding.inflate(getLayoutInflater());
        binding.toolbar.btnBack.setOnClickListener(view -> finish());
        binding.toolbar.btnHome.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));
        binding.toolbar.toolbarTitle.setText("Pre Loading Inspection");

        return binding;
    }

    @Override
    public void initInspectionView() {
        loadData();
        initAutocompleteAdapter();
    }

    @Override
    public void initObserver() {
        observers();
    }

    @Override
    public void nextAction() {
        if (isValid())
            saveInspection(true);
    }

    @Override
    public void prevAction() {
        if (inspectionViewModel.lastId > 0 && inspectionViewModel.currentId > 1)
            inspectionViewModel.getPreviousData();
        else
            Toast.makeText(this, "No Previous Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void submitAction() {
        if (inspectionViewModel.lastId > 0) {
            inspectionDataModel.setSync(true);
            inspectionViewModel.syncAllData();
        } else
            Toast.makeText(this, "No Data to Sync", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initViewModel() {
        inspectionViewModel = new ViewModelProvider(this).get(InspectionViewModel.class);
        createDataBase();
        inspectionViewModel.initRepository();

    }

    void createDataBase() {
        inspectionViewModel.dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
    }

    void loadData() {
        inspectionViewModel.getLastInspection();
    }


    private void initOtherSpinnerData(int i) {

        final List<String> list3 = inspectionViewModel.inspectionRepository.getStackList(i);

        stackAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list3);
        binding.spStack.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        stackAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if (inspectionDataModel != null) {
            binding.spStack.setSelection(inspectionDataModel.getStack());
        }

    }

    void setAdapter(ArrayList<Data> dataList) {
        binding.tvMax.setVisibility(View.VISIBLE);
        binding.tvMin.setVisibility(View.VISIBLE);
        binding.tvActual.setVisibility(View.VISIBLE);
        inspectionAdapter = new InspectionAdapter(dataList);
        binding.rvList.setAdapter(inspectionAdapter);
    }

    private void initAutocompleteAdapter() {
        final List<String> list = inspectionViewModel.inspectionRepository.getRackLoadingData();

        autoCompleteAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.autoCompleteLoadingNum.setAdapter(autoCompleteAdapter);

        binding.autoCompleteLoadingNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                initSpinnerData(i);
            }
        });
    }

    private void setItemCode(int i) {

        final List<String> list1 = inspectionViewModel.inspectionRepository.getItemCode(i);

        itemCodeAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list1);
        binding.spItemCode.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        itemCodeAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if (inspectionDataModel != null) {
            binding.spItemCode.setSelection(inspectionDataModel.getItemCode());
        }
        binding.spItemCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView == null || adapterView.getItemAtPosition(i) == null || adapterView.getItemAtPosition(i).equals("[Select]")) {

                } else if (isNew) {
                    itemList = inspectionViewModel.inspectionRepository.getInspectionItemList(i).dataList;
                    setAdapter(itemList);
                }

                setWareHouse(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setWareHouse(int i) {

        final List<String> list2 = inspectionViewModel.inspectionRepository.getWareHouseList(i);

        warehouseAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list2);
        binding.spWarehouse.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        warehouseAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if (inspectionDataModel != null) {
            binding.spWarehouse.setSelection(inspectionDataModel.getWareHouse());
        }
        binding.spWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initOtherSpinnerData(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSpinnerData(int i) {
        final List<String> list = inspectionViewModel.inspectionRepository.getOrderNumber(i);

        orderNumAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.spOrderNum.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        orderNumAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if (inspectionDataModel != null) {
            binding.spOrderNum.setSelection(inspectionDataModel.getOrderNumber());
        }
        binding.spOrderNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setItemCode(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean isValid() {
        if (binding.autoCompleteLoadingNum.getText() == null || binding.autoCompleteLoadingNum.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Input Rake loading number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spOrderNum.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Order number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spOrderNum.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Order number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spItemCode.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Itemcode", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spWarehouse.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Warehouse", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spStack.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Stack", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!validateItemList()) {
            Toast.makeText(this,
                    "Enter Actual Value", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    boolean validateItemList() {
        boolean isValid = true;
        for (Data data : itemList) {
            if (TextUtils.isEmpty(data.getActualValue())) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    void observers() {
        inspectionViewModel.inserSuccessLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (inspectionViewModel.lastId > 0 && inspectionViewModel.currentId + 1 <= inspectionViewModel.lastId){

                }else{
                    inspectionViewModel.getLastInspection();
                }
            }
        });

        inspectionViewModel.previousInspection.observe(this, new Observer<InspectionDataModel>() {
            @Override
            public void onChanged(InspectionDataModel dataModel) {
                inspectionViewModel.currentId = dataModel.getId();
                previousID = dataModel.getId();
                isNew = false;
                setInspection(dataModel);
            }
        });
    }

    void setInspection(InspectionDataModel inspectionDataModel) {
        this.inspectionDataModel = inspectionDataModel;
        binding.autoCompleteLoadingNum.setText(inspectionDataModel.getRakeLoadingNumber());
        setOrderNumber(inspectionDataModel.getRakeLoadingNumber());
        binding.tvSync.setVisibility((inspectionDataModel.isSync()) ? View.VISIBLE : View.GONE);
        itemList = inspectionDataModel.getItems();
        setAdapter(inspectionDataModel.getItems());
        toggleAction(!inspectionDataModel.isSync());
    }

    void setOrderNumber(String rake) {
        if (rake.equals("20/B124/RAK/0000001/2021")) {
            initSpinnerData(0);
        } else {
            initSpinnerData(1);
        }
    }

    void toggleAction(boolean isEnable) {
        binding.autoCompleteLoadingNum.setEnabled(isEnable);
        binding.spItemCode.setEnabled(isEnable);
        binding.spOrderNum.setEnabled(isEnable);
        binding.spWarehouse.setEnabled(isEnable);
        binding.spStack.setEnabled(isEnable);
        binding.tvSync.setEnabled(isEnable);
        binding.btSubmit.setEnabled(isEnable);
        inspectionAdapter.setSync(!isEnable);
        inspectionAdapter.notifyDataSetChanged();
    }

    void saveInspection(boolean isNext) {
        InspectionDataModel inspectionDataModel = new InspectionDataModel();
        inspectionDataModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        inspectionDataModel.setOrderNumber(binding.spOrderNum.getSelectedItemPosition());
        inspectionDataModel.setItemCode(binding.spItemCode.getSelectedItemPosition());
        inspectionDataModel.setWareHouse(binding.spWarehouse.getSelectedItemPosition());
        inspectionDataModel.setStack(binding.spStack.getSelectedItemPosition());
        inspectionDataModel.setItems(itemList);
        if (previousID != 0)
            inspectionDataModel.setId(previousID);

        if (isNext) {
            if (inspectionViewModel.lastId > 0 && inspectionViewModel.currentId + 1 <= inspectionViewModel.lastId) {
                callSaveInspection(inspectionDataModel);
                inspectionViewModel.getNextData();
            } else {
                if (this.inspectionDataModel != null && this.inspectionDataModel.isSync()) {
                    Toast.makeText(this,
                            "No Next Data", Toast.LENGTH_SHORT).show();
                } else {
                    callSaveInspection(inspectionDataModel);
                    resetInspectionScreen();
                }
            }
        } else {
            inspectionDataModel.setSync(true);
            callSaveInspection(inspectionDataModel);
            binding.tvSync.setVisibility(View.VISIBLE);
        }
    }

    void callSaveInspection(InspectionDataModel minspectionDataModel) {
        if (binding.tvSync.getVisibility() == View.GONE) {
            this.inspectionDataModel = minspectionDataModel;
            inspectionViewModel.addData(minspectionDataModel);
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
        }
    }

    void resetInspectionScreen() {
        previousID = 0;
        binding.spItemCode.setSelection(0);
        binding.spWarehouse.setSelection(0);
        binding.spStack.setSelection(0);
        binding.spItemCode.setSelection(0);
        binding.rvList.setAdapter(null);
        binding.tvMin.setVisibility(View.GONE);
        binding.tvMax.setVisibility(View.GONE);
        binding.tvActual.setVisibility(View.GONE);
        binding.tvSync.setVisibility(View.GONE);
        isNew = true;
        inspectionDataModel = null;
        toggleAction(true);
    }
}