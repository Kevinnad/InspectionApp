package com.example.androidassignment.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidassignment.database.model.Data;
import com.example.androidassignment.databinding.RowItemBinding;

import java.util.List;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.ViewHolder> {

    public InspectionAdapter(List<Data> localDataSet){
        this.localDataSet = localDataSet;
        this.isSync = isSync;
    }

    private List<Data> localDataSet;
    private boolean isSync;

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RowItemBinding binding;

        public ViewHolder(RowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Data dataset, boolean isSync) {
            binding.tvMax.setText(dataset.getMaxValue());
            binding.tvMin.setText(dataset.getMinValue());
            binding.tvActual.setText(dataset.getActualValue());
            binding.tvName.setText(dataset.getName());

            binding.tvActual.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    dataset.setActualValue(editable.toString());
                }
            });

            if(isSync)
                binding.tvActual.setEnabled(false);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RowItemBinding binding = RowItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);

        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.bind(localDataSet.get(position), isSync);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
