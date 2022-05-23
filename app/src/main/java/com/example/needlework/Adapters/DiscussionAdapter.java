package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.Models.DiscussionsResponse;
import com.example.needlework.NetWork.Models.KnittingPatternResponse;
import com.example.needlework.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionHolder> {

    public DiscussionAdapter(List<DiscussionsResponse> mDiscussions, Context context) {
        this.mDiscussions = mDiscussions;
        this.context = context;
    }

    public  class DiscussionHolder extends RecyclerView.ViewHolder{

        public TextView tvTheme;
        public TextView tv_nickname;
        public TextView tv_category;
        public TextView tvRating;
        public TextView tv_date;

        public DiscussionHolder(@NonNull View itemView) {
            super(itemView);

            tvTheme = itemView.findViewById(R.id.tvTheme);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_category = itemView.findViewById(R.id.tv_category);
            tvRating = itemView.findViewById(R.id.tvRating);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

    private List<DiscussionsResponse> mDiscussions;
    private Context context;


    @NonNull
    @Override
    public DiscussionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_discussions, parent, false);
        return new DiscussionAdapter.DiscussionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionHolder holder, int position) {
        final DiscussionsResponse discussions = mDiscussions.get(position);

        holder.tvTheme.setText(discussions.getTheme());
        holder.tv_nickname.setText(String.valueOf(discussions.getUserId()));
        holder.tv_category.setText(String.valueOf(discussions.getCategoryofDiscussionsId()));
        holder.tvRating.setText(String.valueOf(discussions.getRating()));
        holder.tv_date.setText(discussions.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mDiscussions.size();
    }
}
