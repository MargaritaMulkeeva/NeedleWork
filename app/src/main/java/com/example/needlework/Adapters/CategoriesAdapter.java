package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {


    public class CategoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnAdapterItemClickListener listener;

        public TextView name;

        public CategoriesHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.tvCategory);
        }
        public void setName(String title) {
            name.setText(title);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    private List<CategoriesOfPatternResponseBody> mCategories;
    private Context context;
    private OnAdapterItemClickListener listener;

    public CategoriesAdapter(List<CategoriesOfPatternResponseBody> mCategories, Context context, OnAdapterItemClickListener listener) {
        this.mCategories = mCategories;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_categories, parent, false);
        return new CategoriesHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        final CategoriesOfPatternResponseBody categories = mCategories.get(position);

        holder.setName(categories.getName());
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}
