package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.Models.CategoriesOfPatternResponse;
import com.example.needlework.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {


    public class CategoriesHolder extends RecyclerView.ViewHolder{

        public TextView name;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvCategory);
        }
        public void setName(String title) {
            name.setText(title);
        }
    }

    private List<CategoriesOfPatternResponse> mCategories;
    private Context context;

    public CategoriesAdapter(List<CategoriesOfPatternResponse> mCategories, Context context) {
        this.mCategories = mCategories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_categories, parent, false);
        return new CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        final CategoriesOfPatternResponse categories = mCategories.get(position);

        holder.setName(categories.getName());
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}
