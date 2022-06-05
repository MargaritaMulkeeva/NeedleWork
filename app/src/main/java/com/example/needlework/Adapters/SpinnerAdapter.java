package com.example.needlework.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.ApiHandler;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.SpinnerHolder> {
    public class SpinnerHolder extends RecyclerView.ViewHolder{

        public SpinnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public SpinnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SpinnerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
