package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.Models.knittingPatterns.GetAllKnittingPatterns;
import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllPatternAdapter extends RecyclerView.Adapter<AllPatternAdapter.AllPatternHolder> {

    public AllPatternAdapter(List<GetAllKnittingPatterns> mAllPatterns, Context context, OnAdapterItemClickListener listener) {
        this.mAllPatterns = mAllPatterns;
        this.context = context;
        this.listener = listener;
    }

    public class AllPatternHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text_name;
        public TextView tvRating;
        public TextView text_descriprion;
        public ImageView img_photo;

        OnAdapterItemClickListener listener;

        public AllPatternHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            text_name = itemView.findViewById(R.id.text_name);
            tvRating = itemView.findViewById(R.id.tvRating);
            text_descriprion = itemView.findViewById(R.id.text_descriprion);
            img_photo = itemView.findViewById(R.id.img_photo);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    private List<GetAllKnittingPatterns> mAllPatterns;
    private Context context;
    private OnAdapterItemClickListener listener;

    @NonNull
    @Override
    public AllPatternHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_pattern, parent, false);
        return new AllPatternAdapter.AllPatternHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPatternHolder holder, int position) {
        final GetAllKnittingPatterns allPattern = mAllPatterns.get(position);

        holder.text_name.setText(allPattern.getName());
        holder.text_descriprion.setText(allPattern.getDescription());
        holder.tvRating.setText(String.format("%.1f", allPattern.getRating()));
        Picasso.with(context)
                .load(allPattern.getImageOfProduct()).into(holder.img_photo);
    }

    @Override
    public int getItemCount() {
        return mAllPatterns.size();
    }
}
