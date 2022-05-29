package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.PatternHolder> {

    public class PatternHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text_name;
        public TextView tvRating;
        public TextView text_descriprion;
        public ImageView img_photo;

        OnAdapterItemClickListener listener;

        public PatternHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
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

    private List<KnittingPatternResponseBody> mPatterns;
    private Context context;
    private OnAdapterItemClickListener listener;

    public PatternAdapter(List<KnittingPatternResponseBody> mPatterns, Context context, OnAdapterItemClickListener listener) {
        this.mPatterns = mPatterns;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PatternHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_pattern, parent, false);
        return new PatternAdapter.PatternHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PatternHolder holder, int position) {
        final KnittingPatternResponseBody pattern = mPatterns.get(position);

        holder.text_name.setText(pattern.getName());
        holder.text_descriprion.setText(pattern.getDescription());
        holder.tvRating.setText(String.valueOf(pattern.getRating()));
        Picasso.with(context)
                .load(pattern.getImageOfProduct()).into(holder.img_photo);
    }

    @Override
    public int getItemCount() {
        return mPatterns.size();
    }
}
