package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.discussions.GetAllDiscussions;
import com.example.needlework.R;

import java.util.List;

public class AllDiscusionsAdapter extends RecyclerView.Adapter<AllDiscusionsAdapter.AllDiscusionsHolder> {

    public class AllDiscusionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private OnAdapterItemClickListener listener;

        public TextView tvTheme;
        public TextView tv_nickname;
        public TextView tv_category;
        public TextView tvRating;
        public TextView tv_date;

        public AllDiscusionsHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);

            this.listener = this.listener;
            itemView.setOnClickListener(this);

            tvTheme = itemView.findViewById(R.id.tvTheme);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_category = itemView.findViewById(R.id.tv_category);
            tvRating = itemView.findViewById(R.id.tvRating);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    private List<GetAllDiscussions> mDiscussions;
    private Context context;
    private OnAdapterItemClickListener listener;

    public AllDiscusionsAdapter(List<GetAllDiscussions> mDiscussions, Context context, OnAdapterItemClickListener listener) {
        this.mDiscussions = mDiscussions;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AllDiscusionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_discussions, parent, false);
        return new AllDiscusionsAdapter.AllDiscusionsHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDiscusionsHolder holder, int position) {
        final GetAllDiscussions discussions = mDiscussions.get(position);

        holder.tvTheme.setText(discussions.getTheme());
        holder.tv_nickname.setText(String.valueOf(discussions.getUserId()));
        holder.tv_category.setText(String.valueOf(discussions.getCategoryOfDiscussionsId()));
        holder.tvRating.setText(String.valueOf(discussions.getRating()));
        holder.tv_date.setText(discussions.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mDiscussions.size();
    }
}
