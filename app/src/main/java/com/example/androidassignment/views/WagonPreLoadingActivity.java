package com.example.androidassignment.views;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidassignment.NothingSelectedSpinnerAdapter;
import com.example.androidassignment.R;
import com.example.androidassignment.base.BaseInspectionActivity;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.WagonPreLoadingDataModel;
import com.example.androidassignment.databinding.ActivityWagonPreLoadingBinding;
import com.example.androidassignment.viewmodel.WagonPreLoadingViewModel;

import java.util.ArrayList;
import java.util.List;

public class WagonPreLoadingActivity extends BaseInspectionActivity<ActivityWagonPreLoadingBinding> {

    ActivityWagonPreLoadingBinding binding;
    WagonPreLoadingViewModel inspectionViewModel;
    ArrayList<Data> itemList;
    int previousID = 0;
    boolean isNew = true;
    private WagonPreLoadingDataModel inspectionDataModel;
    ArrayAdapter<String> stackAdapter;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayAdapter<String> orderNumAdapter;
    ArrayAdapter<String> warehouseAdapter;


    @Override
    public ActivityWagonPreLoadingBinding getBinding() {
        binding = ActivityWagonPreLoadingBinding.inflate(getLayoutInflater());
        binding.toolbar.toolbarTitle.setText("Wagon pre unloading Inspection");
        binding.toolbar.btnBack.setOnClickListener(view -> finish());
        binding.toolbar.btnHome.setOnClickListener(view ->  startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )));
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
        if (inspectionViewModel.lastId > 0){
            inspectionViewModel.syncAllData();
        }else
            Toast.makeText(this, "No Data to Sync", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initViewModel() {
        inspectionViewModel = new ViewModelProvider(this).get(WagonPreLoadingViewModel.class);
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

        final List<String> list3 = inspectionViewModel.inspectionRepository.getWagonSerialNum(i);

        stackAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list3);
        binding.spWagonSerialNo.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        stackAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if (inspectionDataModel != null) {
            binding.spWagonSerialNo.setSelection(inspectionDataModel.getWagonSerialNum());
        }

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


    private void setWareHouse(int i) {

        final List<String> list2 = inspectionViewModel.inspectionRepository.getWagonCapacity(i);

        warehouseAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list2);
        binding.spWagonCapacity.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        warehouseAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if (inspectionDataModel != null) {
            binding.spWagonCapacity.setSelection(inspectionDataModel.getWagonCapacity());
        }
        binding.spWagonCapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        final List<String> list = inspectionViewModel.inspectionRepository.getWagonType(i);

        orderNumAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.spWagonType.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        orderNumAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        if (inspectionDataModel != null) {
            binding.spWagonType.setSelection(inspectionDataModel.getWagonType());
        }
        binding.spWagonType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setWareHouse(i);

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
        } else if (binding.spWagonType.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Wagon Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spWagonCapacity.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Wagon Capacity", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spWagonSerialNo.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Wagon Serial Number", Toast.LENGTH_SHORT).show();
            return false;
        }   else if (binding.rgTarpauline.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Check Tarpauline placed at doors after closing", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etDoorLocked.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Check Door Locked or Not", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.rgSeal.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Check Seal Placed or Not", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etRemarks.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Remarks", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etNoOfTrucks.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Number Of Trucks", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etSpillBeans.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Spillage beans collecte", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etSealNum.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Seal Numbers", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etTotalWeight.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Total Weight", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    void observers() {
        inspectionViewModel.inserSuccessLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                inspectionViewModel.getLastInspection();
            }
        });

        inspectionViewModel.previousInspection.observe(this, new Observer<WagonPreLoadingDataModel>() {
            @Override
            public void onChanged(WagonPreLoadingDataModel dataModel) {
                inspectionViewModel.currentId = dataModel.getId();
                previousID = dataModel.getId();
                isNew = false;
                setInspection(dataModel);
            }
        });
    }

    void setInspection(WagonPreLoadingDataModel inspectionDataModel) {
        this.inspectionDataModel = inspectionDataModel;
        binding.autoCompleteLoadingNum.setText(inspectionDataModel.getRakeLoadingNumber());
        binding.etTotalWeight.setText(inspectionDataModel.getTotalWeight());
        binding.etNoOfTrucks.setText(inspectionDataModel.getTruckNo());
        binding.etRemarks.setText(inspectionDataModel.getRemarks());
        binding.etSpillBeans.setText(inspectionDataModel.getSpillBeans());
        binding.etSealNum.setText(inspectionDataModel.getSealNum());
        setOrderNumber(inspectionDataModel.getRakeLoadingNumber());

        if (inspectionDataModel.getSeal() == 0) {
            binding.rbSealYes.setChecked(true);
        } else
            binding.rbSealNo.setChecked(true);
        binding.etDoorLocked.setText(inspectionDataModel.getDoorLocked());

        if (inspectionDataModel.getTarpauline() == 0) {
            binding.rbTrapaulineYes.setChecked(true);
        } else
            binding.rbTrapaulineNo.setChecked(true);

        binding.tvSync.setVisibility((inspectionDataModel.isSync()) ? View.VISIBLE : View.GONE);
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
        binding.spWagonSerialNo.setEnabled(isEnable);
        binding.spWagonCapacity.setEnabled(isEnable);
        binding.spWagonType.setEnabled(isEnable);
        binding.tvSync.setEnabled(isEnable);
    }

    void saveInspection(boolean isNext) {
        WagonPreLoadingDataModel inspectionDataModel = new WagonPreLoadingDataModel();
        inspectionDataModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        inspectionDataModel.setWagonCapacity(binding.spWagonCapacity.getSelectedItemPosition());
        inspectionDataModel.setWagonType(binding.spWagonType.getSelectedItemPosition());
        inspectionDataModel.setWagonSerialNum(binding.spWagonSerialNo.getSelectedItemPosition());
        inspectionDataModel.setTruckNo(binding.etNoOfTrucks.getText().toString());
        inspectionDataModel.setSpillBeans(binding.etSpillBeans.getText().toString());
        inspectionDataModel.setTotalWeight(binding.etTotalWeight.getText().toString());
        inspectionDataModel.setRemarks(binding.etRemarks.getText().toString());
        inspectionDataModel.setDoorLocked(binding.etDoorLocked.getText().toString());
        inspectionDataModel.setSealNum(binding.etSealNum.getText().toString());

        if(binding.rbTrapaulineNo.isChecked())
            inspectionDataModel.setTarpauline(1);
        else
            inspectionDataModel.setTarpauline(0);

        if(binding.rbSealNo.isChecked())
            inspectionDataModel.setSeal(1);
        else
            inspectionDataModel.setSeal(0);
        if (previousID != 0)
            inspectionDataModel.setId(previousID);

        if (isNext) {
            if (inspectionViewModel.lastId > 0 && inspectionViewModel.currentId + 1 <= inspectionViewModel.lastId) {
                inspectionViewModel.getNextData();
            } else {

                if (this.inspectionDataModel != null && this.inspectionDataModel.isSync()) {
                    Toast.makeText(this,
                            "No Next Data", Toast.LENGTH_SHORT).show();
                }else {
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

    void callSaveInspection(WagonPreLoadingDataModel minspectionDataModel) {
        if (binding.tvSync.getVisibility() == View.GONE) {
            this.inspectionDataModel = minspectionDataModel;
            inspectionViewModel.addData(minspectionDataModel);
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
        }
    }

    void resetInspectionScreen() {
        previousID = 0;
        binding.spWagonSerialNo.setSelection(0);
        binding.spWagonType.setSelection(0);
        binding.spWagonCapacity.setSelection(0);
        binding.etNoOfTrucks.setText("");
        binding.etSpillBeans.setText("");
        binding.etRemarks.setText("");
        binding.etTotalWeight.setText("");
        binding.etDoorLocked.setText("");
        binding.etSealNum.setText("");
        binding.rgSeal.clearCheck();
        binding.rgTarpauline.clearCheck();
        binding.tvSync.setVisibility(View.GONE);
        isNew = true;
        inspectionDataModel = null;
        toggleAction(true);
    }

}