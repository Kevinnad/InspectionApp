package com.example.androidassignment.views;

import static com.example.androidassignment.dataStore.CommonDataStore.RAKE_LOADING_NO;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidassignment.NothingSelectedSpinnerAdapter;
import com.example.androidassignment.R;
import com.example.androidassignment.adapter.InspectionAdapter;
import com.example.androidassignment.base.BaseInspectionActivity;
import com.example.androidassignment.dataStore.CommonDataStore;
import com.example.androidassignment.database.database.DataBaseProvider;
import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.database.model.InspectionDataModel;
import com.example.androidassignment.database.model.ItemCode;
import com.example.androidassignment.database.model.RakeLoadingNumber;
import com.example.androidassignment.database.model.StackModel;
import com.example.androidassignment.database.model.WareHouse;
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
    ArrayAdapter<String> warehouseAdapter;
    ArrayAdapter<String> itemCodeAdapter;
    InspectionAdapter inspectionAdapter;

    List<RakeLoadingNumber> rakeLoadingNumbers;

    String orderNumber = "";
    String selectedRakeLoading = "";

    @Override
    public ActivityPreLoadingInspectionBinding getBinding() {
        binding = ActivityPreLoadingInspectionBinding.inflate(getLayoutInflater());
        binding.toolbar.btnBack.setOnClickListener(view -> finish());
        binding.toolbar.btnHome.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));
        binding.toolbar.toolbarTitle.setText("Pre Loading Inspection");
        binding.tvDelete.setOnClickListener(view -> {
            showAlertDialogForDelete("Are you sure? Do you want to delete the inspection", new OnAlertButtonClickListener() {
                @Override
                public void onPositiveButton() {
                    if (inspectionDataModel != null)
                        inspectionViewModel.deleteData(inspectionDataModel);
                    else
                        Toast.makeText(InspectionActivity.this, "This Inspection cannot be deleted", Toast.LENGTH_LONG).show();
                }
            });
        });

        return binding;
    }


    @Override
    public void initInspectionView() {
        new Handler().postDelayed(() -> initAutocompleteAdapter(), 500);
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
            inspectionViewModel.getPreviousData(orderNumber);
        else
            Toast.makeText(this, "No Previous Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void submitAction() {
        if (inspectionViewModel.lastId > 0) {
            showAlertDialogForDelete("Are you sure? You want to Sync the Complete Order", new OnAlertButtonClickListener() {
                @Override
                public void onPositiveButton() {
                    if (inspectionDataModel != null) {
                        inspectionDataModel.setSync(true);
                    }
                    inspectionViewModel.syncAllData();
                }
            });
        } else
            Toast.makeText(this, "No Data to Sync", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initViewModel() {
        inspectionViewModel = new ViewModelProvider(this).get(InspectionViewModel.class);
        createDataBase();
        inspectionViewModel.initRepository();
        inspectionViewModel.inspectionRepository.seedItemCode();
        inspectionViewModel.inspectionRepository.seedWareHouse();
        inspectionViewModel.inspectionRepository.seedStack();

    }

    void createDataBase() {
        inspectionViewModel.dataBaseProvider = DataBaseProvider.getInstance(getApplicationContext());
    }

    void loadData() {
        inspectionViewModel.getLastInspection(orderNumber);
    }


    private void initOtherSpinnerData(String wareHouse) {

        inspectionViewModel.getStackList(wareHouse);

        inspectionViewModel.stackLiveData.observe(this, stackModels -> {

            List<String> list = new ArrayList<>();
            for (int i = 0; i < stackModels.size(); i++) {
                StackModel stackModel = stackModels.get(i);
                if (isNew && !stackModel.submitedTo)
                    list.add(stackModel.value);
            }
            if (inspectionDataModel != null)
                list.add(inspectionDataModel.getStack());

            stackAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_dropdown_item_1line, list);
            binding.spStack.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            stackAdapter,
                            R.layout.contact_spinner_row_nothing_selected,
                            this));
            if (inspectionDataModel != null) {
                binding.spStack.setSelection(1);
            }
        });


    }

    void setAdapter(ArrayList<Data> dataList) {
        binding.tvMax.setVisibility(View.VISIBLE);
        binding.tvMin.setVisibility(View.VISIBLE);
        binding.tvActual.setVisibility(View.VISIBLE);
        inspectionAdapter = new InspectionAdapter(dataList);
        binding.rvList.setAdapter(inspectionAdapter);
    }

    private void initAutocompleteAdapter() {

        inspectionViewModel.getRakeLoadingNumber();
        inspectionViewModel.rakeLoadingLiveData.observe(this, rakeLoadingNumbers -> {
            if (rakeLoadingNumbers != null && rakeLoadingNumbers.size() > 0) {
                this.rakeLoadingNumbers = rakeLoadingNumbers;
                final List<String> list = new ArrayList<>();
                for (RakeLoadingNumber rakeLoading : rakeLoadingNumbers
                ) {
                    list.add(rakeLoading.value);
                }

                ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_dropdown_item_1line, list);
                binding.autoCompleteLoadingNum.setAdapter(autoCompleteAdapter);

                selectedRakeLoading = CommonDataStore.getStringInPrefernce(this, RAKE_LOADING_NO);
                if (!TextUtils.isEmpty(selectedRakeLoading)) {
                    binding.autoCompleteLoadingNum.setText(selectedRakeLoading);
                } else if (list.size() == 1) {
                    binding.autoCompleteLoadingNum.setText(list.get(0));
                }

                binding.autoCompleteLoadingNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedRakeLoading = list.get(i);
                        CommonDataStore.saveStringInPrefernce(InspectionActivity.this, RAKE_LOADING_NO, list.get(i));
                        initSpinnerData();
                    }
                });

                initSpinnerData();
            }
        });

        binding.ivRakeLoadingAdd.setOnClickListener(v -> {

            if (rakeLoadingNumbers != null && rakeLoadingNumbers.size() > 1) {
                showAlertDialogForDelete("Currently you can have only two Rake Loading Number", () -> {
                });
            } else if (rakeLoadingNumbers != null && rakeLoadingNumbers.size() > 0) {
                showAlertDialogForDelete("Do you want to add a Rake Loading Number?", () -> {
                    inspectionViewModel.addRakeLoadingNumber(1);
                });
            } else {
                showAlertDialogForDelete("Do you want to add a Rake Loading Number?", () -> {
                    inspectionViewModel.addRakeLoadingNumber(0);
                });
            }
        });

    }

    private void setItemCode(String orderNO) {

        inspectionViewModel.getItemCode(orderNO);

        inspectionViewModel.itemCodeLiveData.observe(this, itemCodes -> {

            List<String> list = new ArrayList<>();
            for (int i = 0; i < itemCodes.size(); i++) {
                ItemCode itemCode = itemCodes.get(i);
                if (isNew && !itemCode.submitedTo)
                    list.add(itemCode.value);
            }
            if (inspectionDataModel != null)
                list.add(inspectionDataModel.getItemCode());

            itemCodeAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_dropdown_item_1line, list);
            binding.spItemCode.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            itemCodeAdapter,
                            R.layout.contact_spinner_row_nothing_selected,
                            // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                            this));
            if (inspectionDataModel != null) {
                binding.spItemCode.setSelection(1);
            }
            binding.spItemCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        setWareHouse(list.get(i - 1));
                        if (isNew) {
                            itemList = inspectionViewModel.inspectionRepository.getInspectionItemList(i).dataList;
                            setAdapter(itemList);
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });

    }

    private void setWareHouse(String itemCode) {

        inspectionViewModel.getWareHouse(itemCode);

        inspectionViewModel.wareHouseLiveData.observe(this, wareHouses -> {

            final List<String> list = new ArrayList<>();

            for (int i = 0; i < wareHouses.size(); i++) {
                WareHouse wareHouse = wareHouses.get(i);
                if (isNew && !wareHouse.submitedTo)
                    list.add(wareHouse.value);
            }
            if (inspectionDataModel != null)
                list.add(inspectionDataModel.getWareHouse());

            warehouseAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_dropdown_item_1line, list);
            binding.spWarehouse.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            warehouseAdapter,
                            R.layout.contact_spinner_row_nothing_selected,
                            // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                            this));
            if (inspectionDataModel != null) {
                binding.spWarehouse.setSelection(1);
            }
            binding.spWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0)
                        initOtherSpinnerData(list.get(i - 1));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });


    }

    private void initSpinnerData() {
        CommonDataStore commonDataStore = new CommonDataStore();
        final List<String> list = commonDataStore.getOrderNumber(selectedRakeLoading);

        ArrayAdapter<String> orderNumAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        binding.spOrderNum.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        orderNumAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        this));
        binding.spOrderNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    orderNumber = list.get(i - 1);
                    setItemCode(orderNumber);
                    loadData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private boolean isValid() {
        if (binding.spItemCode.getSelectedItem() == null) {
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

            return false;
        }
        return true;
    }

    boolean validateItemList() {
        boolean isValid = true;
        for (Data data : itemList) {
            if (TextUtils.isEmpty(data.getActualValue())) {
                isValid = false;
                Toast.makeText(this,
                        "Enter Actual Value", Toast.LENGTH_SHORT).show();
                break;
            } else if (Integer.parseInt(data.getMinValue()) > Integer.parseInt(data.getActualValue())) {
                isValid = false;
                Toast.makeText(this,
                        "Actual Value is less than minimum value", Toast.LENGTH_SHORT).show();
                break;
            } else if (Integer.parseInt(data.getMaxValue()) < Integer.parseInt(data.getActualValue())) {
                isValid = false;
                Toast.makeText(this,
                        "Actual Value is greater than maximum value", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return isValid;
    }

    void observers() {
        inspectionViewModel.inserSuccessLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (inspectionViewModel.lastId > 0 && inspectionViewModel.currentId + 1 <= inspectionViewModel.lastId) {

                } else {
                    inspectionViewModel.getLastInspection(orderNumber);
                }
            }
        });

        inspectionViewModel.deleteSuccessLiveData.observe(this, aBoolean -> resetInspectionScreen());

        inspectionViewModel.previousInspection.observe(this, dataModel -> {
            inspectionViewModel.currentId = dataModel.getId();
            previousID = dataModel.getId();
            isNew = false;
            setInspection(dataModel);
        });

        inspectionViewModel.lastInspectionCompleted.observe(this, inspectionDataModel -> {
            if(inspectionDataModel != null){
                binding.tvCount.setText(""+(inspectionDataModel.getCount()+1));
            }
            resetInspectionScreen();
        });
    }

    void setInspection(InspectionDataModel inspectionDataModel) {
        this.inspectionDataModel = inspectionDataModel;
        binding.autoCompleteLoadingNum.setText(inspectionDataModel.getRakeLoadingNumber());
        binding.tvSync.setVisibility((inspectionDataModel.isSync()) ? View.VISIBLE : View.INVISIBLE);
        binding.tvDelete.setVisibility((!inspectionDataModel.isSync()) ? View.VISIBLE : View.INVISIBLE);
        itemList = inspectionDataModel.getItems();
        setAdapter(inspectionDataModel.getItems());
        toggleAction(!inspectionDataModel.isSync());
        setItemCode(orderNumber);
        binding.tvCount.setText(""+inspectionDataModel.getCount());
    }

    void toggleAction(boolean isEnable) {
        binding.autoCompleteLoadingNum.setEnabled(isEnable);
        binding.spItemCode.setEnabled(isEnable);
        binding.spWarehouse.setEnabled(isEnable);
        binding.spStack.setEnabled(isEnable);
        binding.tvSync.setEnabled(isEnable);
        binding.btSubmit.setEnabled(isEnable);
        if(inspectionAdapter != null){
            inspectionAdapter.setSync(!isEnable);
            inspectionAdapter.notifyDataSetChanged();
        }
    }

    void saveInspection(boolean isNext) {
        InspectionDataModel inspectionDataModel = new InspectionDataModel();
        inspectionDataModel.setRakeLoadingNumber(binding.autoCompleteLoadingNum.getText().toString());
        inspectionDataModel.setOrderNumber(orderNumber);
        inspectionDataModel.setItemCode(binding.spItemCode.getSelectedItem().toString());
        inspectionDataModel.setWareHouse(binding.spWarehouse.getSelectedItem().toString());
        inspectionDataModel.setStack(binding.spStack.getSelectedItem().toString());
        inspectionDataModel.setItems(itemList);
        if (previousID != 0)
            inspectionDataModel.setId(previousID);

        if (isNext) {
            if (inspectionViewModel.lastId > 0 && inspectionViewModel.currentId + 1 <= inspectionViewModel.lastId) {
                callSaveInspection(inspectionDataModel);
                inspectionViewModel.getNextData(orderNumber);
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
            binding.tvDelete.setVisibility(View.GONE);
        }
    }

    void callSaveInspection(InspectionDataModel minspectionDataModel) {
        if (binding.tvSync.getVisibility() == View.INVISIBLE) {
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
        binding.tvSync.setVisibility(View.INVISIBLE);
        binding.tvDelete.setVisibility(View.VISIBLE);
        isNew = true;
        inspectionDataModel = null;
        toggleAction(true);
        setItemCode(orderNumber);
    }
}