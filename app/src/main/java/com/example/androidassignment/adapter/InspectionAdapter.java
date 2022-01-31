package com.example.androidassignment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidassignment.databinding.RowItemBinding;
import com.example.androidassignment.views.DataModel;

import java.util.ArrayList;
import java.util.List;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.ViewHolder> {

    public InspectionAdapter(List<DataModel> localDataSet){
        this.localDataSet = localDataSet;
    }

    private List<DataModel> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RowItemBinding binding;

        public ViewHolder(RowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DataModel dataset) {
            binding.tvMax.setText(dataset.max);
            binding.tvMin.setText(dataset.min);
            binding.tvActual.setText(dataset.actual);
            binding.tvName.setText(dataset.name);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RowItemBinding binding = RowItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);

        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.bind(localDataSet.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
