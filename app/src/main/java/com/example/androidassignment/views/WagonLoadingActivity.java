package com.example.androidassignment.views;

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
import com.example.androidassignment.database.model.WagonLoadingDataModel;
import com.example.androidassignment.database.model.WagonPreLoadingDataModel;
import com.example.androidassignment.databinding.ActivityWagonLoadingInspectionBinding;
import com.example.androidassignment.databinding.ActivityWagonPreLoadingBinding;
import com.example.androidassignment.viewmodel.WagonLoadingViewModel;
import com.example.androidassignment.viewmodel.WagonPreLoadingViewModel;

import java.util.ArrayList;
import java.util.List;

public class WagonLoadingActivity extends BaseInspectionActivity<ActivityWagonLoadingInspectionBinding> {

    ActivityWagonLoadingInspectionBinding binding;
    WagonLoadingViewModel inspectionViewModel;
    ArrayList<Data> itemList;
    int previousID = 0;
    boolean isNew = true;
    private WagonLoadingDataModel inspectionDataModel;
    ArrayAdapter<String> stackAdapter;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayAdapter<String> orderNumAdapter;
    ArrayAdapter<String> warehouseAdapter;


    @Override
    public ActivityWagonLoadingInspectionBinding getBinding() {
        binding = ActivityWagonLoadingInspectionBinding.inflate(getLayoutInflater());
        binding.toolbar.toolbarTitle.setText("Wagon Pre Loading Inspection");

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
            inspectionDataModel.setSync(true);
            inspectionViewModel.syncAllData();
        }else
            Toast.makeText(this, "No Data to Sync", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initViewModel() {
        inspectionViewModel = new ViewModelProvider(this).get(WagonLoadingViewModel.class);
        createDataBase();
        inspectionViewModel.initRepository();

    }

    void createDataBase() {
        inspectionViewModel.dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
    }

    void loadData() {
        inspectionViewModel.getLastInspection();
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
        }
        else if (binding.etWagonId.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Wagon ID", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etWagonNo.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Wagon Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.spWagonType.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Wagon Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.spWagonCapacity.getSelectedItem() == null) {
            Toast.makeText(this,
                    "Select Wagon Capacity", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etPreviousCargo.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Previous", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etWagonSerialNo.getText().toString().isEmpty()) {
            Toast.makeText(this,
                    "Enter Wagon Serial Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.clFloor.rgTarpauline.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Check Whether floor is cleaned", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.clSidleHoles.rgTarpauline.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Check Sides for holes are checked", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.clTopHoles.rgTarpauline.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Check Top for holes are checked", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.clGround.rgTarpauline.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Check Tarpauline placed at ground between Wagon & Truck", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.clTfloors.rgTarpauline.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Check Tarpauline In Wagon Floors", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.clSides.rgTarpauline.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,
                    "Check Tarpauline In sides", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

        inspectionViewModel.previousInspection.observe(this, new Observer<WagonLoadingDataModel>() {
            @Override
            public void onChanged(WagonLoadingDataModel dataModel) {
                inspectionViewModel.currentId = dataModel.getId();
                previousID = dataModel.getId();
                isNew = false;
                setInspection(dataModel);
            }
        });
    }

    void setInspection(WagonLoadingDataModel inspectionDataModel) {
        this.inspectionDataModel = inspectionDataModel;
        binding.autoCompleteLoadingNum.setText(inspectionDataModel.getRakeLoadingNumber());
        binding.etWagonNo.setText(inspectionDataModel.getWagonNumber());
        binding.etWagonId.setText(inspectionDataModel.getWagonID());
        setOrderNumber(inspectionDataModel.getRakeLoadingNumber());
        if (inspectionDataModel.getRemarks() == 0) {
            binding.clFloor.yes.setChecked(true);
        } else
            binding.clFloor.No.setChecked(true);
        if (inspectionDataModel.getSeal() == 0) {
            binding.clSides.yes.setChecked(true);
        } else
            binding.clSides.No.setChecked(true);
        if (inspectionDataModel.getDoorLocked() == 0) {
            binding.clTopHoles.yes.setChecked(true);
        } else
            binding.clTopHoles.No.setChecked(true);
        if (inspectionDataModel.getTarpauline() == 0) {
            binding.clGround.yes.setChecked(true);
        } else
            binding.clGround.No.setChecked(true);
        if (inspectionDataModel.getTarpauline() == 0) {
            binding.clTfloors.yes.setChecked(true);
        } else
            binding.clTfloors.No.setChecked(true);

        if (inspectionDataModel.getTarpauline() == 0) {
            binding.clSides.yes.setChecked(true);
        } else
            binding.clSides.No.setChecked(true);

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
        binding.etWagonSerialNo.setEnabled(isEnable);
        binding.spWagonCapacity.setEnabled(isEnable);
        binding.spWagonType.setEnabled(isEnable);
        binding.tvSync.setEnabled(isEnable);
        binding.btSubmit.setEnabled(isEnable);
    }

    void saveInspection(boolean isNext) {
        WagonLoadingDataModel inspectionDataModel = new WagonLoadingDataModel();
        inspectionDataModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        inspectionDataModel.setWagonCapacity(binding.spWagonCapacity.getSelectedItemPosition());
        inspectionDataModel.setWagonType(binding.spWagonType.getSelectedItemPosition());
        inspectionDataModel.setWagonSerialNum(binding.etWagonSerialNo.getText().toString());
        inspectionDataModel.setWagonID(binding.etWagonId.getText().toString());
        inspectionDataModel.setWagonNumber(binding.etWagonNo.getText().toString());

        if(binding.clTopHoles.yes.isChecked())
            inspectionDataModel.setTarpauline(1);
        else
            inspectionDataModel.setTarpauline(0);
        if(binding.clSides.yes.isChecked())
            inspectionDataModel.setRemarks(1);
        else
            inspectionDataModel.setRemarks(0);
        if(binding.clTfloors.yes.isChecked())
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

    void callSaveInspection(WagonLoadingDataModel minspectionDataModel) {
        if (binding.tvSync.getVisibility() == View.GONE) {
            this.inspectionDataModel = minspectionDataModel;
            inspectionViewModel.addData(minspectionDataModel);
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
        }
    }

    void resetInspectionScreen() {
        previousID = 0;
        binding.spWagonType.setSelection(0);
        binding.spWagonCapacity.setSelection(0);
        binding.etPreviousCargo.setText("");
        binding.tvSync.setVisibility(View.GONE);
        isNew = true;
        inspectionDataModel = null;
        toggleAction(true);
    }
    

}