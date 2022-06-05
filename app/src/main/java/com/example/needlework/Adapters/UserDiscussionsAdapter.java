package com.example.needlework.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.Discussions.EditDiscussions;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.user.GetUserResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDiscussionsAdapter extends RecyclerView.Adapter<UserDiscussionsAdapter.UserDiscussionsHolder> {

    public UserDiscussionsAdapter(List<DiscussionsResponseBody> mDiscussions, Context context, OnAdapterItemClickListener listener) {
        this.mDiscussions = mDiscussions;
        this.context = context;
        this.listener = listener;
    }

    public class UserDiscussionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnAdapterItemClickListener listener;

        public TextView tvTheme;
        public TextView tv_category;
        public TextView tvRating;
        public TextView tv_date;
        public Button editButton;

        public UserDiscussionsHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            tvTheme = itemView.findViewById(R.id.tvName);
            tv_category = itemView.findViewById(R.id.tv_category);
            tvRating = itemView.findViewById(R.id.tvRating);
            tv_date = itemView.findViewById(R.id.tv_date);
            editButton = itemView.findViewById(R.id.btnChange);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    private List<DiscussionsResponseBody> mDiscussions;
    private Context context;
    private OnAdapterItemClickListener listener;
    private ApiService service = ApiHandler.getmInstance().getService();

    @NonNull
    @Override
    public UserDiscussionsAdapter.UserDiscussionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_user_discussions, parent, false);
        return new UserDiscussionsAdapter.UserDiscussionsHolder(view, listener);
    }

    @Override
    public int getItemCount() {
        return mDiscussions.size();
    }

    @Override
    public void onBindViewHolder(@NonNull UserDiscussionsAdapter.UserDiscussionsHolder holder, int position) {
        final DiscussionsResponseBody discussions = mDiscussions.get(position);

        holder.tvTheme.setText(discussions.getTheme());
        holder.tvRating.setText(String.valueOf(discussions.getRating()));
        holder.tv_date.setText(discussions.getCreatedAt().substring(0, 10));
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditDiscussions.class);
                intent.putExtra("discussionId", discussions.getId());
                context.startActivity(intent);
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.getCategoriesOfDiscussion().enqueue(new Callback<List<CategoriesOfPatternResponseBody>>() {
                    @Override
                    public void onResponse(Call<List<CategoriesOfPatternResponseBody>> call, Response<List<CategoriesOfPatternResponseBody>> response) {
                        for (int i = 0; i < response.body().size(); i++) {
                            if (response.body().get(i).getId() == discussions.getCategoryOfDiscussionsId()) {
                                holder.tv_category.setText(response.body().get(i).getName());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CategoriesOfPatternResponseBody>> call, Throwable t) {

                    }
                });
            }
        });
    }
}
